package com.london.tudee.presentation.screens.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.london.tudee.R
import com.london.tudee.domain.entities.Category
import com.london.tudee.domain.entities.Priority
import com.london.tudee.domain.entities.Task
import com.london.tudee.domain.entities.TaskStatus
import com.london.tudee.domain.services.CategoryService
import com.london.tudee.domain.services.TaskService
import com.london.tudee.presentation.screens.task.task_modify.TaskModifyUiState
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
import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class TasksScreenViewModel(
    private val taskService: TaskService,
    private val categoryService: CategoryService
) : ViewModel(), TasksInteractions {

    private val _uiState = MutableStateFlow(TasksUiState())
    val uiState = _uiState.asStateFlow()

    private val _taskUiState = MutableStateFlow(TaskModifyUiState())
    val taskUiState = _taskUiState.asStateFlow()

    init {
        loadCategories()
        updateDateByAction(date = _uiState.value.date, arrowAction = ArrowActions.None)
        initializeDoneTasks()
        initializeToDoTasks()
        initializeInProgressTasks()
    }

    //region get tasks
    fun initializeDoneTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            val targetDate = _uiState.value.date.toLocalDate()

            val (startOfDayMillis, endOfDayMillis) = TasksScreenUtils.getDayRangeMillis(targetDate)
            taskService.getTasksForDay(
                start = startOfDayMillis, end = endOfDayMillis, status = TaskStatus.DONE
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
                start = startOfDayMillis, end = endOfDayMillis, status = TaskStatus.IN_PROGRESS
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
                start = startOfDayMillis, end = endOfDayMillis, status = TaskStatus.TODO
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

        val updatedDays = _uiState.value.days.mapIndexed { index, day ->
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

    //region delete task
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
                    initializeDoneTasks()
                    initializeInProgressTasks()
                    initializeToDoTasks()

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

    fun loadCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                categoryService.getAll().collect { categories ->
                    _taskUiState.update { currentState ->
                        currentState.copy(
                            categories = categories,
                            selectedCategory = currentState.selectedCategory,
                            categoryIcons = categories.map { it.iconRes }
                        )
                    }
                    validateForm()
                }
            }.onFailure {
                _taskUiState.update {
                    it.copy(stateMessage = R.string.some_error_happened)
                }
            }
        }
    }
    //endregion

    override fun saveTask() {
        val currentState = _taskUiState.value
        if (!currentState.isFormValid) return

        viewModelScope.launch(Dispatchers.IO) {
            _taskUiState.update { it.copy(isLoading = true) }

            runCatching {
                val task = Task(
                    id = 0,
                    title = currentState.title.trim(),
                    description = currentState.description.trim(),
                    taskStatus = TaskStatus.TODO,
                    priority = currentState.selectedPriority,
                    categoryId = currentState.selectedCategory?.id ?: 1,
                    timeStamp = currentState.selectedDate?.let {
                        Instant.fromEpochMilliseconds(
                            it
                        )
                    }
                        ?: Clock.System.now()
                )
                currentState.selectedCategory?.let { category ->
                    categoryService.edit(category.copy(taskCount = category.taskCount + 1))
                }
                taskService.add(task)
                _taskUiState.update {
                    it.copy(
                        isLoading = false,
                        stateMessage = R.string.add_task_successfully,
                        showBottomSheet = false
                    )
                }
            }.onFailure {
                _taskUiState.update {
                    it.copy(
                        isLoading = false,
                        stateMessage = R.string.some_error_happened
                    )
                }
            }
        }
    }

    override fun updateTitle(title: String) {
        _taskUiState.update { it.copy(title = title) }
        validateForm()
    }

    override fun updateDescription(description: String) {
        _taskUiState.update { it.copy(description = description) }
    }

    override fun updateDate(date: Long) {
        _taskUiState.update { it.copy(selectedDate = date) }
        validateForm()
    }

    override fun updatePriority(priority: Priority) {
        _taskUiState.update { it.copy(selectedPriority = priority) }
        validateForm()
    }

    override fun updateCategory(category: Category) {
        _taskUiState.update { it.copy(selectedCategory = category) }
        validateForm()
    }

    override fun validateForm() {
        _taskUiState.update { currentState ->
            currentState.copy(
                isFormValid = currentState.title.isNotBlank()
                        && currentState.selectedDate != null
                        && currentState.selectedCategory != null
            )
        }
    }

    override fun showDatePicker() {
        _taskUiState.update { it.copy(showDatePicker = true) }
    }

    override fun hideDatePicker() {
        _taskUiState.update { it.copy(showDatePicker = false) }
    }

    override fun showBottomSheet() {
        _taskUiState.update { it.copy(showBottomSheet = true) }
    }

    override fun hideBottomSheet() {
        _taskUiState.update {
            it.copy(
                showBottomSheet = false,
            )
        }
    }

    override fun clearMessages() {
        _taskUiState.update {
            it.copy(
                stateMessage = null
            )
        }
    }
}