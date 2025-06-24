package com.london.tudee.presentation.screens.tasks

import com.london.tudee.domain.entities.Task
import kotlinx.datetime.Clock

data class FilterTasksUiState(
    val isLoading: Boolean = true,
    val errMessage: String? = null,
    val categoryId: Int? = null,
    val currentMonth: String = "",
    val currentYear: String = "",
    val days: List<DaysOfMonth> = listOf(),
    val selectedDays: List<Boolean> = listOf(),
    var date: Long = Clock.System.now().toEpochMilliseconds(),
    val tasksCount: Int? = null,
    val isDaySelected: Boolean? = null,
    val doneTasks: List<Task> = listOf(),
    val inProgressTasks: List<Task> = listOf(),
    val toDoTasks: List<Task> = listOf(),
    val dayItemIndex: Int = 0
)

data class DaysOfMonth(
    val dayOfMonth: String,
    val dayOfWeek: String,
    var isSelected: Boolean = false,
    var date: Long = 0L
)

enum class ArrowActions {
    Next,
    Previous,
    None
}