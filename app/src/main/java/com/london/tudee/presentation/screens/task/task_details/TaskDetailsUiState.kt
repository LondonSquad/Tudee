package com.london.tudee.presentation.screens.task.task_details

import com.london.tudee.domain.entities.Priority
import com.london.tudee.domain.entities.Task
import com.london.tudee.domain.entities.TaskStatus
import java.util.Date

data class TaskDetailsUiState(
    val categoryIcon: Int=0,
    val task:Task=Task(
        id = 1,
        title = "",
        description = "",
        taskStatus = TaskStatus.TODO,
        priority = Priority.HIGH,
        categoryId = 6,
        timeStamp = Date()
    )
)
