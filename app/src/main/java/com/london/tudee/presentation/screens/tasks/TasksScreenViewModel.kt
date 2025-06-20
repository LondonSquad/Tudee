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

class TasksScreenViewModel(
    private val taskService: TaskService,
    private val categoryService: CategoryService
) : ViewModel() {
    private val _uiState = MutableStateFlow(FilterTasksUiState())
    val uiState = _uiState.asStateFlow()

    init {

        getAllTasks()
        getDoneTasks()
        getToDoTasks()
        getInProgressTasks()
    }

    private fun getAllTasks() {
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
                        allTasks = tasks.map {
                            it.copy(categoryId = categoryService.getIconResById(it.categoryId))
                        },
                    )
                }
            }
        }
    }


    private fun getDoneTasks() {
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
                        doneTasks = tasks.map {
                            it.copy(categoryId = categoryService.getIconResById(it.categoryId))
                        },
                    )
                }
            }
        }
    }

    private fun getInProgressTasks() {
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
                        inProgressTasks = tasks.map {
                            it.copy(categoryId = categoryService.getIconResById(it.categoryId))
                        }
                    )
                }
            }
        }
    }

    private fun getToDoTasks() {
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
                        toDoTasks = tasks.map {
                            it.copy(categoryId = categoryService.getIconResById(it.categoryId))
                        }
                    )
                }
            }
        }
    }

}