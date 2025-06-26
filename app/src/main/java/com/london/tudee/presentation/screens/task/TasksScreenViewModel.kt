package com.london.tudee.presentation.screens.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.london.tudee.domain.entities.TaskStatus
import com.london.tudee.domain.services.CategoryService
import com.london.tudee.domain.services.TaskService
import com.london.tudee.presentation.utils.DateFormatter.toDayOfWeekShort
import com.london.tudee.presentation.utils.DateFormatter.toLocalDate
import com.london.tudee.presentation.utils.DateFormatter.toLongDate
import com.london.tudee.presentation.utils.TasksScreenUtils
import com.london.tudee.presentation.utils.TasksScreenUtils.lengthOfMonth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class TasksScreenViewModel(
    private val taskService: TaskService,
    private val categoryService: CategoryService
) : ViewModel() {

    private val _uiState = MutableStateFlow(TasksUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadCategories()
        updateDateByAction(date = _uiState.value.date, arrowAction = ArrowActions.None)
        initializeDoneTasks()
        initializeToDoTasks()
        initializeInProgressTasks()
    }

    fun initializeDoneTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            val targetDate = _uiState.value.date.toLocalDate()

            val (startOfDayMillis, endOfDayMillis) = TasksScreenUtils.getDayRangeMillis(targetDate)
            taskService.getTasksForDay(
                start = startOfDayMillis, end = endOfDayMillis, taskStatus = TaskStatus.DONE
            ).catch { throwable ->
                _uiState.update {
                    it.copy(isLoading = false, errMessage = throwable.message)
                }
            }.collect { tasks ->
                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        errMessage = null,
                        doneTasks = tasks.map {
                            it.copy(categoryId = it.categoryId)
                        }
                    )
                }
            }
        }
    }

    fun initializeInProgressTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            val targetDate = _uiState.value.date.toLocalDate()
            val (startOfDayMillis, endOfDayMillis) = TasksScreenUtils.getDayRangeMillis(targetDate)

            taskService.getTasksForDay(
                start = startOfDayMillis, end = endOfDayMillis, taskStatus = TaskStatus.IN_PROGRESS
            ).catch { throwable ->
                _uiState.update {
                    it.copy(isLoading = false, errMessage = throwable.message)
                }
            }.collect { tasks ->
                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        errMessage = null,
                        inProgressTasks = tasks.map {
                            it.copy(categoryId = it.categoryId)
                        }
                    )
                }
            }
        }
    }

    fun initializeToDoTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            val targetDate = _uiState.value.date.toLocalDate()
            val (startOfDayMillis, endOfDayMillis) = TasksScreenUtils.getDayRangeMillis(targetDate)
            taskService.getTasksForDay(
                start = startOfDayMillis, end = endOfDayMillis, taskStatus = TaskStatus.TODO
            ).catch { throwable ->
                _uiState.update {
                    it.copy(isLoading = false, errMessage = throwable.message)
                }
            }.collect { tasks ->
                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        errMessage = null,
                        toDoTasks = tasks.map {
                            it.copy(categoryId = it.categoryId)
                        }
                    )
                }
            }
        }
    }

    fun updateDateByAction(date: Long, arrowAction: ArrowActions) {

        val currentLocalDate = date.toLocalDate()
        val previouslySelectedDate = _uiState.value.date.toLocalDate()

        val targetDate = when (arrowAction) {
            ArrowActions.Next -> currentLocalDate.plus(DatePeriod(months = 1))
            ArrowActions.Previous -> currentLocalDate.minus(DatePeriod(months = 1))
            ArrowActions.None -> currentLocalDate
        }

        val dayToSelect = if (arrowAction == ArrowActions.None) {
            previouslySelectedDate.dayOfMonth
        } else {
            minOf(
                previouslySelectedDate.dayOfMonth,
                targetDate.lengthOfMonth(targetDate.toLongDate())
            )
        }
        val newSelectedDate = LocalDate(targetDate.year, targetDate.month, dayToSelect)

        _uiState.update { currentState ->
            val daysOfMonth = (1..targetDate.lengthOfMonth(_uiState.value.date)).map { day ->
                val dateForDay = LocalDate(targetDate.year, targetDate.month, day)
                val dayOfWeek = dateForDay.toDayOfWeekShort()
                DaysOfMonth(
                    dayOfMonth = day.toString(),
                    dayOfWeek = dayOfWeek,
                    isSelected = dateForDay == newSelectedDate,
                    date = dateForDay.toLongDate()
                )
            }
            currentState.copy(
                days = daysOfMonth,
                date = targetDate.toLongDate()
            )
        }
        if (arrowAction != ArrowActions.None) {
            onDateSelected(newSelectedDate.toLongDate())
        }
    }

    fun onDaySelected(indexOfSelectedDay: Int) {
        val days = _uiState.value.days.toMutableList()
        val currentDate = _uiState.value.date.toLocalDate()
        days.forEach { it.isSelected = false }
        days[indexOfSelectedDay].isSelected = true
        val selectedDayOfMonth = days[indexOfSelectedDay].dayOfMonth.toInt()
        val selectedDate =
            LocalDate(currentDate.year, Month(currentDate.monthNumber), selectedDayOfMonth)
        val dateInMillis = selectedDate.toLongDate()

        days[indexOfSelectedDay].date = dateInMillis
        _uiState.update {
            it.copy(days = days, dayItemIndex = indexOfSelectedDay, date = dateInMillis)
        }
        initializeDoneTasks()
        initializeInProgressTasks()
        initializeToDoTasks()

    }

    fun onDateSelected(datePickerDate: Long) {
        val targetLocalDate = datePickerDate.toLocalDate()

        updateDateByAction(date = datePickerDate, arrowAction = ArrowActions.None)

        val updatedDays = _uiState.value.days.map { day ->
            val dayOfMonth = day.dayOfMonth.toInt()
            val dayLocalDate = LocalDate(targetLocalDate.year, targetLocalDate.month, dayOfMonth)
            day.copy(
                isSelected = (dayLocalDate == targetLocalDate),
                date = dayLocalDate.toLongDate()
            )
        }
        _uiState.update {
            it.copy(
                days = updatedDays,
                date = datePickerDate,
                dayItemIndex = targetLocalDate.dayOfMonth - 1
            )
        }
        initializeDoneTasks()
        initializeInProgressTasks()
        initializeToDoTasks()
    }


    fun showDeleteDialog(taskId: Int?) {
        _uiState.update {
            it.copy(selectedTaskId = taskId, isDeleteDialogVisible = true)
        }
    }

    fun dismissDeleteDialog() {
        _uiState.update {
            it.copy(selectedTaskId = null, isDeleteDialogVisible = false)
        }
    }

    fun deleteTask(
        onSuccess: () -> Unit,
        onError: (Throwable) -> Unit = {}
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val selectedTaskId = uiState.value.selectedTaskId
            if (selectedTaskId != null) {
                runCatching {
                    val task = taskService.getById(selectedTaskId)
                    taskService.delete(task)
                    onSuccess()
                    dismissDeleteDialog()
                    initializeDoneTasks()
                    initializeInProgressTasks()
                    initializeToDoTasks()

                }.onFailure {
                    withContext(Dispatchers.Main) {
                        onError(it)
                    }
                }
            } else {
                withContext(Dispatchers.Main) {
                    dismissDeleteDialog()
                }
            }
        }
    }

    private fun loadCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                categoryService.getAll().collect { categories ->
                    _uiState.update { currentState ->
                        currentState.copy(
                            categories = categories,
                            categoryIcons = categories.map { it.iconRes }
                        )
                    }
                }
            }.onFailure { throwable ->
                _uiState.update {
                    it.copy(errMessage = throwable.message)
                }
            }
        }
    }
}