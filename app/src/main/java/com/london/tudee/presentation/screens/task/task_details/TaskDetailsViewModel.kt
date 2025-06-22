package com.london.tudee.presentation.screens.task.task_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.london.tudee.domain.entities.Priority
import com.london.tudee.domain.entities.Task
import com.london.tudee.domain.entities.TaskStatus
import com.london.tudee.domain.services.CategoryService
import com.london.tudee.domain.services.TaskService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.Instant

class TaskDetailsViewModel(
    private val taskService: TaskService, private val categoryService: CategoryService
) : ViewModel() {

    private val _uiState = MutableStateFlow<TaskDetailsUiState>(TaskDetailsUiState())
    val uiState: StateFlow<TaskDetailsUiState> = _uiState

    fun loadTask(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                val task = taskService.getById(id)
                val icon = categoryService.getIconResById(task.categoryId)
                _uiState.update {
                    it.copy(task = task, categoryIcon = icon)
                }
            }.isFailure.also {
                if (it) _uiState.update {
                    it.copy(errorMessages = "Something went wrong task not found or no category icon")
                }
            }
        }
    }

    fun onClickMove() {
        viewModelScope.launch {
            val task = uiState.value.task
            val newStatus = when (task.taskStatus) {
                TaskStatus.TODO -> TaskStatus.IN_PROGRESS
                TaskStatus.IN_PROGRESS -> TaskStatus.DONE
                TaskStatus.DONE -> return@launch
            }
            val updatedTask = task.copy(taskStatus = newStatus)
            withContext(Dispatchers.IO) {
                runCatching {
                    taskService.edit(updatedTask)
                }.isFailure.also {
                    if (it) _uiState.update {
                        it.copy(errorMessages = "Something went wrong cant update task")
                    }
                }
            }
            _uiState.update {
                it.copy(task = updatedTask)
            }
        }
    }

    fun hideBottomSheet() {
        _uiState.update {
            it.copy(isVisibleDetailsBottomSheet = false)
        }
    }

    fun onEditClick() {
        _uiState.update {
            it.copy(isVisibleEditBottomSheet = true)
        }
    }

}
