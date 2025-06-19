package com.london.tudee.presentation.screens.task.task_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.london.tudee.domain.entities.TaskStatus
import com.london.tudee.domain.services.CategoryService
import com.london.tudee.domain.services.TaskService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TaskDetailsViewModel(
    private val taskService: TaskService, private val categoryService: CategoryService
) : ViewModel() {

    private val _uiState = MutableStateFlow<TaskDetailsUiState>(TaskDetailsUiState())
    val uiState: StateFlow<TaskDetailsUiState> = _uiState

    fun loadTask(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val task = taskService.getById(id)
            val icon = categoryService.getIconPathById(task.categoryId)
            _uiState.update {
                it.copy(task = task, categoryIcon = icon)
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
                taskService.edit(updatedTask)
            }
            _uiState.update {
                it.copy(task = updatedTask)
            }

        }
    }

}
