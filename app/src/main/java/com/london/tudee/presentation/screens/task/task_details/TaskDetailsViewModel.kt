package com.london.tudee.presentation.screens.task.task_details

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class TaskDetailsViewModel() : ViewModel() {

    private val _task = mutableStateOf<TaskDetailsUiState>(TaskDetailsUiState())
    val taskDetailsUiState: TaskDetailsUiState = _task.value

    fun loadTask(id: Int) {
            _task.value = TaskDetailsUiState()
    }


}