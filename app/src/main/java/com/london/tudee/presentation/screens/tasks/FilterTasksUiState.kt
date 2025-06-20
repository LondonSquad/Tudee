package com.london.tudee.presentation.screens.tasks

import com.london.tudee.domain.entities.Task

data class FilterTasksUiState(
    val isLoading: Boolean = true,
    val errMessage: String? = null,
    val categoryId: Int? = null,
    val currentDay: String? = null,
    val currentDayOfWeek: String? = null,
    val currentMonth: String? = null,
    val currentYear: String? = null,
    val tasksCount: Int? = null,
    val allTasks: List<Task> = listOf(),
    val isDaySelected: Boolean? = null,
    val doneTasks: List<Task> = listOf(),
    val inProgressTasks: List<Task> = listOf(),
    val toDoTasks: List<Task> = listOf(),
)