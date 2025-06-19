package com.london.tudee.presentation.screens.task.confirm_delete_task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.london.tudee.domain.services.TaskService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class ConfirmDeleteTaskViewModel(
    private val taskService: TaskService
) : ViewModel() {

    private val _uiState = MutableStateFlow(ConfirmDeleteTaskUiState())
    val uiState: StateFlow<ConfirmDeleteTaskUiState> = _uiState.asStateFlow()

    fun showDialog(taskId: Int) {
        _uiState.update { it.copy(taskId = taskId, isVisible = true) }
    }

    fun dismissDialog() {
        _uiState.update { it.copy(isVisible = false) }
    }

    fun deleteTask(
        onSuccess: () -> Unit,
        onError: (Throwable) -> Unit = {}
    ) {
        viewModelScope.launch {
            val taskId = _uiState.value.taskId
            if (taskId != null) {
                try {
                    val task = taskService.getById(taskId)
                    taskService.delete(task)
                    onSuccess()
                } catch (e: Exception) {
                    onError(e)
                }
            }
        }
    }
}


