package com.london.tudee.presentation.screens.task.task_details

import com.london.tudee.domain.entities.Priority
import com.london.tudee.domain.entities.Task
import com.london.tudee.domain.entities.TaskStatus
import kotlinx.datetime.Clock

data class TaskDetailsBottomSheetUiState(
    val categoryIcon: String = "",
    val task: Task = Task(
        id = 1,
        title = "",
        description = "",
        taskStatus = TaskStatus.TODO,
        priority = Priority.HIGH,
        categoryId = 6,
        timeStamp = Clock.System.now()
    ),
)