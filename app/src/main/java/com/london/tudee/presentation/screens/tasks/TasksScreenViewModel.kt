package com.london.tudee.presentation.screens.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.london.tudee.domain.entities.Priority
import com.london.tudee.domain.entities.Task
import com.london.tudee.domain.entities.TaskStatus
import com.london.tudee.domain.services.CategoryService
import com.london.tudee.domain.services.TaskService
import com.london.tudee.presentation.screens.tasks.TasksScreenUtils.getDayRangeMillis
import com.london.tudee.presentation.screens.tasks.TasksScreenUtils.lengthOfMonth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime

class TasksScreenViewModel(
    private val taskService: TaskService,
    private val categoryService: CategoryService
) : ViewModel() {

    private val _uiState = MutableStateFlow(FilterTasksUiState())
    val uiState = _uiState.asStateFlow()

    init {
        addTestTask()
        getTargetDates(date = _uiState.value.date, arrowAction = ArrowActions.None)
        getDoneTasks()
        getToDoTasks()
        getInProgressTasks()
    }


    //region get tasks
    private fun addTestTask() {
        viewModelScope.launch(Dispatchers.IO) {
            taskService.add(
                Task(
                    title = "33333333333",
                    description = "kimo",
                    taskStatus = TaskStatus.DONE,
                    priority = Priority.MEDIUM,
                    categoryId = 1,
                )
            )
        }
    }

    fun getDoneTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            val targetDate = Instant.fromEpochMilliseconds(_uiState.value.date)
                .toLocalDateTime(TimeZone.currentSystemDefault()).date

            val (startOfDayMillis, endOfDayMillis) = getDayRangeMillis(targetDate)
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

    fun getInProgressTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            val targetDate = Instant.fromEpochMilliseconds(_uiState.value.date)
                .toLocalDateTime(TimeZone.currentSystemDefault()).date

            val (startOfDayMillis, endOfDayMillis) = getDayRangeMillis(targetDate)

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

    fun getToDoTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            val targetDate = Instant.fromEpochMilliseconds(_uiState.value.date)
                .toLocalDateTime(TimeZone.currentSystemDefault()).date
            val (startOfDayMillis, endOfDayMillis) = getDayRangeMillis(targetDate)
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
    //endregion


    fun getTargetDates(date: Long, arrowAction: ArrowActions) {
        val currentDate = Instant.fromEpochMilliseconds(date)
            .toLocalDateTime(TimeZone.currentSystemDefault()).date

        val selectedDate = Instant.fromEpochMilliseconds(_uiState.value.date)
            .toLocalDateTime(TimeZone.currentSystemDefault()).date

        val targetDate = when (arrowAction) {
            ArrowActions.Next -> currentDate.plus(DatePeriod(months = 1))
            ArrowActions.Previous -> currentDate.minus(DatePeriod(months = 1))
            ArrowActions.None -> currentDate
        }

        _uiState.update { currentState ->
            val daysOfMonth = (1..targetDate.lengthOfMonth(_uiState.value.date)).map { day ->
                val dateForDay = LocalDate(targetDate.year, targetDate.month, day)
                val dayOfWeek = dateForDay.dayOfWeek.name.take(3).lowercase()
                    .replaceFirstChar { it.uppercase() }

                DaysOfMonth(
                    dayOfMonth = day.toString(),
                    dayOfWeek = dayOfWeek,
                    isSelected = dateForDay == selectedDate,
                )
            }
            currentState.copy(
                days = daysOfMonth,
                date = targetDate.atStartOfDayIn(TimeZone.currentSystemDefault())
                    .toEpochMilliseconds()
            )
        }
    }


    fun onDayCardSelected(indexOfSelectedDay: Int) {
        val days = _uiState.value.days.toMutableList()
        val currentDate = Instant.fromEpochMilliseconds(_uiState.value.date)
            .toLocalDateTime(TimeZone.currentSystemDefault()).date

        days.forEach { it.isSelected = false }
        days[indexOfSelectedDay].isSelected = true
        val selectedDayOfMonth = days[indexOfSelectedDay].dayOfMonth.toInt()
        val selectedDate =
            LocalDate(currentDate.year, Month(currentDate.monthNumber), selectedDayOfMonth)
        val dateInMillis =
            selectedDate.atStartOfDayIn(TimeZone.currentSystemDefault()).toEpochMilliseconds()

        days[indexOfSelectedDay].date = dateInMillis
        _uiState.update {
            it.copy(days = days, dayItemIndex = indexOfSelectedDay, date = dateInMillis)
        }
    }

    fun onDateSelected(datePickerDate: Long) {
        val targetLocalDate = Instant.fromEpochMilliseconds(datePickerDate)
            .toLocalDateTime(TimeZone.currentSystemDefault()).date

        getTargetDates(date = datePickerDate, arrowAction = ArrowActions.None)

        val updatedDays = _uiState.value.days.mapIndexed { index, day ->
            val dayOfMonth = day.dayOfMonth.toInt()
            val dayLocalDate = LocalDate(targetLocalDate.year, targetLocalDate.month, dayOfMonth)
            day.copy(
                isSelected = (dayLocalDate == targetLocalDate),
                date = dayLocalDate.atStartOfDayIn(TimeZone.currentSystemDefault())
                    .toEpochMilliseconds()
            )
        }
        _uiState.update {
            it.copy(
                days = updatedDays,
                date = datePickerDate,
                dayItemIndex = targetLocalDate.dayOfMonth - 1
            )
        }
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
                try {
                    val task = taskService.getById(selectedTaskId)
                    taskService.delete(task)
                    onSuccess()
                    dismissDeleteDialog()
                    getDoneTasks()
                    getToDoTasks()
                    getInProgressTasks()

                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        onError(e)
                    }
                }
            } else {
                withContext(Dispatchers.Main) {
                    dismissDeleteDialog()
                }
            }
        }
    }
}