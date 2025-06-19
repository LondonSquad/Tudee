package com.london.tudee.presentation.screens.home

import com.london.tudee.presentation.model.TaskUiState

data class HomeUiState(
    val isLoading: Boolean = true,
    val errMessage: String? = null,
    val allTasks: List<TaskUiState> = listOf(),
    val doneTasks: List<TaskUiState> = listOf(),
    val inProgressTasks: List<TaskUiState> = listOf(),
    val toDoTasks: List<TaskUiState> = listOf(),
)
