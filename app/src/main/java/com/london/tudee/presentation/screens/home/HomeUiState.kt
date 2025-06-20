package com.london.tudee.presentation.screens.home

import com.london.tudee.domain.entities.Task

data class HomeUiState(
    val isLoading: Boolean = true,
    val errMessage: String? = null,
    val allTasks: List<Task> = listOf(),
    val doneTasks: List<Task> = listOf(),
    val inProgressTasks: List<Task> = listOf(),
    val toDoTasks: List<Task> = listOf(),
)
