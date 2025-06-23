package com.london.tudee.presentation.screens.home


import androidx.lifecycle.viewModelScope
import com.london.tudee.domain.entities.TaskStatus
import com.london.tudee.domain.services.CategoryService
import com.london.tudee.domain.services.TaskService
import com.london.tudee.presentation.base.BaseCreateTaskViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val taskService: TaskService,
    private val categoryService: CategoryService
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
                        isLoading = false,
                        errMessage = null,
                        inProgressTasks = tasks
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
                        isLoading = false,
                        errMessage = null,
                        toDoTasks = tasks
                    )
                }
            }
        }
    }
}