package com.london.tudee.presentation.screens.home

import com.london.tudee.domain.entities.Task
import com.london.tudee.presentation.screens.task.task_details.TaskDetailsBottomSheetUiState

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
data class NotificationSliderUiState(
    val title: String = "",
    val subtitle: String = "",
    val emoji: Int = -1,
    val tudeePicture: Int = -1,
)
