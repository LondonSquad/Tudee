package com.london.tudee.presentation.screens.tasks

import com.london.tudee.presentation.model.TaskUiState

data class FilterTasksUiState(
    val isLoading: Boolean = true,
    val errMessage: String? = null,
    val categoryId: Int? = null,
    val currentDay: String? = null,
    val currentDayOfWeek: String? = null,
    val currentMonth: String? = null,
    val currentYear: String? = null,
    val tasksCount: Int? = null,
    val allTasks: List<TaskUiState> = listOf(),
    val isDaySelected: Boolean? = null,
    val doneTasks: List<TaskUiState> = listOf(),
    val inProgressTasks: List<TaskUiState> = listOf(),
    val toDoTasks: List<TaskUiState> = listOf(),
)