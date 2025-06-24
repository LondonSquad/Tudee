package com.london.tudee.presentation.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.london.tudee.R
import com.london.tudee.domain.entities.Category
import com.london.tudee.domain.entities.Priority
import com.london.tudee.domain.entities.Task
import com.london.tudee.domain.entities.TaskStatus
import com.london.tudee.domain.services.CategoryService
import com.london.tudee.domain.services.TaskService
import com.london.tudee.presentation.base.HomeInteractions
import com.london.tudee.presentation.screens.task.add_edit_task_bottom_sheet.AddOrEditTaskUiState
import com.london.tudee.presentation.screens.task.taskdetails.TaskDetailsBottomSheetUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

class HomeViewModel(
    private val taskService: TaskService, private val categoryService: CategoryService
) : ViewModel(), HomeInteractions {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    private val _taskUiState = MutableStateFlow(AddOrEditTaskUiState())
    val taskUiState = _taskUiState.asStateFlow()

    init {
        loadCategories()
        initializeAllTasks()
        initializeDoneTasks()
        initializeTodoTasks()
        initializeInProgressTasks()
    }

    private fun initializeAllTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            taskService.getAll().catch { throwable ->
                _uiState.update {
                    it.copy(isLoading = false, errMessage = throwable.message)
                }
            }.collect { tasks ->
                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        errMessage = null,
                        allTasks = tasks,
                    )
                }
            }
        }
    }

    private fun initializeDoneTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            taskService.getByTaskStatus(TaskStatus.DONE).catch { throwable ->
                _uiState.update {
                    it.copy(isLoading = false, errMessage = throwable.message)
                }
            }.collect { tasks ->
                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        errMessage = null,
                        doneTasks = tasks,
                    )
                }
            }
        }
    }

    private fun initializeInProgressTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            taskService.getByTaskStatus(TaskStatus.IN_PROGRESS).catch { throwable ->
                _uiState.update {
                    it.copy(isLoading = false, errMessage = throwable.message)
                }
            }.collect { tasks ->
                _uiState.update { state ->
                    state.copy(
                        isLoading = false, errMessage = null, inProgressTasks = tasks
                    )
                }
            }
        }
    }

    private fun initializeTodoTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            taskService.getByTaskStatus(TaskStatus.TODO).catch { throwable ->
                _uiState.update {
                    it.copy(isLoading = false, errMessage = throwable.message)
                }
            }.collect { tasks ->
                _uiState.update { state ->
                    state.copy(
                        isLoading = false, errMessage = null, toDoTasks = tasks
                    )
                }
            }
        }
    }

    override fun loadTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                val icon = categoryService.getIconResById(task.categoryId)
                _uiState.update {
                    it.copy(
                        taskDetailBottomSheetUiState = it.taskDetailBottomSheetUiState.copy(
                            task = task, categoryIcon = icon
                        )
                    )
                }
            }.isFailure.also {
                if (it) _uiState.update { state ->
                    state.copy(errMessage = R.string.Something_went_wrong_task_not_found_or_no_category_icon.toString())
                }
            }
        }
    }

    override fun onClickMove() {
        viewModelScope.launch(Dispatchers.IO) {
            val task = uiState.value.taskDetailBottomSheetUiState.task
            var icon = ""
            val newStatus = when (task.taskStatus) {
                TaskStatus.TODO -> TaskStatus.IN_PROGRESS
                TaskStatus.IN_PROGRESS -> TaskStatus.DONE
                TaskStatus.DONE -> return@launch
            }
            val updatedTask = task.copy(taskStatus = newStatus)
            runCatching {
                taskService.edit(updatedTask)
                icon = categoryService.getIconResById(task.categoryId)
            }.isFailure.also {
                if (it) _uiState.update { state ->
                    state.copy(errMessage = R.string.Something_went_wrong_cant_update_task.toString())
                }
            }
            _uiState.update {
                it.copy(
                    taskDetailBottomSheetUiState = TaskDetailsBottomSheetUiState(
                        task = updatedTask, categoryIcon = icon
                    )
                )
            }
        }
    }

    override fun showTaskDetailsBottomSheet() {
        _uiState.update {
            it.copy(isTaskDetailsBottomSheetVisible = true)
        }
    }

    override fun hideTaskDetailsBottomSheet() {
        _uiState.update {
            it.copy(isTaskDetailsBottomSheetVisible = false)
        }
    }

    override fun loadCategories() {
        viewModelScope.launch {
            try {
                categoryService.getAll().collect { categories ->
                    _taskUiState.update { currentState ->
                        currentState.copy(
                            categories = categories,
                            selectedCategory = currentState.selectedCategory,
                            categoryIcons = categories.map { it.iconRes })
                    }
                    validateForm()
                }
            } catch (_: Exception) {
                _taskUiState.update { it.copy(stateMessage = R.string.some_error_happened) }
            }
        }
    }

    override fun initializeForEdit(taskId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _taskUiState.update { it.copy(isLoading = true) }
            try {
                val task = taskService.getById(taskId)
                val category = categoryService.getById(task.categoryId)

                withContext(Dispatchers.Main) {
                    _taskUiState.update { currentState ->
                        currentState.copy(
                            taskId = task.id,
                            title = task.title,
                            description = task.description,
                            selectedDate = task.timeStamp.toEpochMilliseconds(),
                            selectedPriority = task.priority,
                            selectedCategory = category,
                            isEditMode = true,
                            isLoading = false,
                            showBottomSheet = true
                        )
                    }
                    validateForm()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _taskUiState.update {
                        it.copy(
                            stateMessage = R.string.some_error_happened,
                            isLoading = false,
                            showBottomSheet = false
                        )
                    }
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

    override fun updateSelectedDate(date: Long) {
        _taskUiState.update { it.copy(selectedDate = date) }
        validateForm()
    }

    override fun updateSelectedPriority(priority: Priority) {
        _taskUiState.update { it.copy(selectedPriority = priority) }
        validateForm()
    }

    override fun updateSelectedCategory(category: Category) {
        _taskUiState.update { it.copy(selectedCategory = category) }
        validateForm()
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

        viewModelScope.launch {
            delay(500)
            _taskUiState.update {
                it.copy(
                    title = "",
                    description = "",
                    selectedDate = null,
                    selectedPriority = Priority.LOW,
                    selectedCategory = it.categories.firstOrNull(),
                    stateMessage = null,
                    isEditMode = false,
                    taskId = null
                )
            }
            validateForm()
        }
    }

    override fun saveTask() {
        val currentState = _taskUiState.value

        if (!currentState.isFormValid) return

        viewModelScope.launch {
            _taskUiState.update { it.copy(isLoading = true) }

            try {
                val task = if (currentState.isEditMode) {
                    // For editing, preserve the original task status and other fields
                    val originalTask = uiState.value.taskDetailBottomSheetUiState.task
                    Task(
                        id = currentState.taskId ?: 0,
                        title = currentState.title.trim(),
                        description = currentState.description.trim(),
                        taskStatus = originalTask.taskStatus, // Preserve original status
                        priority = currentState.selectedPriority,
                        categoryId = currentState.selectedCategory?.id ?: 1,
                        timeStamp = currentState.selectedDate?.let {
                            Instant.fromEpochMilliseconds(
                                it
                            )
                        }
                            ?: Clock.System.now()
                    )
                } else {
                    // For new tasks, use TODO status
                    Task(
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
                }

                if (currentState.isEditMode) {
                    taskService.edit(task)
                } else {
                    currentState.selectedCategory?.let { category ->
                        categoryService.edit(category.copy(taskCount = category.taskCount + 1))
                    }
                    taskService.add(task)
                }

                _taskUiState.update {
                    it.copy(
                        isLoading = false,
                        stateMessage = if (currentState.isEditMode) R.string.edit_task_successfully
                        else R.string.add_task_successfully,
                        showBottomSheet = false
                    )
                }

            } catch (_: Exception) {
                _taskUiState.update {
                    it.copy(
                        isLoading = false,
                        stateMessage = R.string.some_error_happened
                    )
                }
            }
        }
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

    override fun clearMessages() {
        _taskUiState.update {
            it.copy(
                stateMessage = null
            )
        }
    }

}