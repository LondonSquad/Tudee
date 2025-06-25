package com.london.tudee.presentation.screens.home

import com.london.tudee.domain.entities.Task
import com.london.tudee.presentation.screens.task.taskdetails.TaskDetailsBottomSheetUiState

data class HomeUiState(
    val isDarkMode: Boolean = false,
    val isLoading: Boolean = true,
    val errMessage: String? = null,
    val allTasks: List<Task> = listOf(),
    val doneTasks: List<Task> = listOf(),
    val inProgressTasks: List<Task> = listOf(),
    val toDoTasks: List<Task> = listOf(),
    val taskDetailBottomSheetUiState: TaskDetailsBottomSheetUiState = TaskDetailsBottomSheetUiState(),
    val isTaskDetailsBottomSheetVisible: Boolean = false
)
