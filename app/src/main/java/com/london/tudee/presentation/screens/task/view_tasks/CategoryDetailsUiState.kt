package com.london.tudee.presentation.screens.task.view_tasks

import com.london.tudee.domain.entities.Category
import com.london.tudee.domain.entities.Task


data class CategoryDetailsState(
    val isLoading: Boolean = true,
    val errMessage: String? = null,
    val allTasks: List<Task> = listOf(),
    val doneTasks: List<Task> = listOf(),
    val inProgressTasks: List<Task> = listOf(),
    val toDoTasks: List<Task> = listOf(),
    val category: Category = Category(
        id = 0,
        title = "Default",
        iconRes = "",
        isDefault = false,
        taskCount = 0

    )
)


