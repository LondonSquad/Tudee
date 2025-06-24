package com.london.tudee.presentation.screens.task.taskdetails

import com.london.tudee.domain.entities.Priority
import com.london.tudee.domain.entities.Task
import com.london.tudee.domain.entities.TaskStatus
import kotlinx.datetime.Instant

data class TaskDetailsBottomSheetUiState(
    val categoryIcon: String = "",
    val task: Task = Task(
        id = 1,
        title = "",
        description = "",
        taskStatus = TaskStatus.TODO,
        priority = Priority.HIGH,
        categoryId = 6,
        timeStamp = Instant.parse("2023-09-20T00:00:00Z")
    ),
)
