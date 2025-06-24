package com.london.tudee.presentation.screens.home

import androidx.lifecycle.viewModelScope
import com.london.tudee.R
import com.london.tudee.domain.entities.Task
import com.london.tudee.domain.entities.TaskStatus
import com.london.tudee.domain.services.CategoryService
import com.london.tudee.domain.services.TaskService
import com.london.tudee.presentation.base.BaseCreateTaskViewModel
import com.london.tudee.presentation.screens.task.taskdetails.TaskDetailsBottomSheetUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(
    private val taskService: TaskService, private val categoryService: CategoryService
) : BaseCreateTaskViewModel(
    taskService, categoryService
) {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
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
                            task = task,
                            categoryIcon = icon
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
        viewModelScope.launch {
            val task = uiState.value.taskDetailBottomSheetUiState.task
            var icon = ""
            val newStatus = when (task.taskStatus) {
                TaskStatus.TODO -> TaskStatus.IN_PROGRESS
                TaskStatus.IN_PROGRESS -> TaskStatus.DONE
                TaskStatus.DONE -> return@launch
            }
            val updatedTask = task.copy(taskStatus = newStatus)
            withContext(Dispatchers.IO) {
                runCatching {
                    taskService.edit(updatedTask)
                    icon = categoryService.getIconResById(task.categoryId)
                }.isFailure.also {
                    if (it) _uiState.update { state ->
                        state.copy(errMessage = R.string.Something_went_wrong_cant_update_task.toString())
                    }
                }
            }
            _uiState.update {
                it.copy(
                    taskDetailBottomSheetUiState = TaskDetailsBottomSheetUiState(
                        task = updatedTask,
                        categoryIcon = icon
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

}