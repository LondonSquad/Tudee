package com.london.tudee.presentation.screens.task.task_details

import com.london.tudee.R
import com.london.tudee.domain.entities.Priority
import com.london.tudee.domain.entities.TaskStatus

data class TaskDetailsUiState(
    val taskName: String = "",
    val taskDescription: String = "",
    val taskStatus: TaskStatus = TaskStatus.TODO,
    val taskPriority: Priority = Priority.HIGH,
    val icon: Int=R.drawable.ic_education
)
