package com.london.tudee.presentation.screens.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.london.tudee.domain.entities.TaskStatus
import com.london.tudee.domain.services.CategoryService
import com.london.tudee.domain.services.TaskService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TasksScreenViewModel(
    private val taskService: TaskService,
    private val categoryService: CategoryService
) : ViewModel() {

    private val _uiState = MutableStateFlow(FilterTasksUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getDoneTasks()
        getToDoTasks()
        getInProgressTasks()
    }

    fun getDoneTasks() {
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
                        doneTasks = tasks
                    )
                }
            }
        }
    }

    fun getInProgressTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            taskService.getByTaskStatus(TaskStatus.IN_PROGRESS).catch { throwable ->
                _uiState.update {
                    it.copy(isLoading = false, errMessage = throwable.message)
                }
            }.collect { tasks ->
                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        errMessage = null,
                        inProgressTasks = tasks
                    )
                }
            }
        }
    }

    fun getToDoTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            taskService.getByTaskStatus(TaskStatus.TODO).catch { throwable ->
                _uiState.update {
                    it.copy(isLoading = false, errMessage = throwable.message)
                }
            }.collect { tasks ->
                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        errMessage = null,
                        toDoTasks = tasks
                    )
                }
            }
        }
    }

    fun showDeleteDialog(taskId: Int) {
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