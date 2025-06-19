package com.london.tudee.presentation.screens.tasks

import androidx.compose.runtime.mutableStateListOf
import com.london.tudee.R
import com.london.tudee.presentation.components.priority.Priority
import com.london.tudee.presentation.components.task.Task

data class DateItemClass(
    val dayOfMonth: String, val dayOfWeek: String, var isSelected: Boolean
)

val fakeDates = mutableStateListOf(
    DateItemClass("17", "Mon", false),
    DateItemClass("18", "Tue", false),
    DateItemClass("19", "Wed", true),
    DateItemClass("20", "Thu", false),
    DateItemClass("21", "Fri", false),
    DateItemClass("22", "Sat", false),
    DateItemClass("23", "Sun", false),
)

val tasks1 = listOf(
    Task(
        priority = Priority.MEDIUM,
        iconResId = R.drawable.ic_education,
        title = "Organize Study Desk",
        description = "Review cell structure and functions for tomorrow...",
        date = "2023-09-20"
    ),
    Task(
        priority = Priority.LOW,
        iconResId = R.drawable.ic_education,
        title = "Organize Study Desk",
        description = "",
        date = "2023-09-20"
    ),
    Task(
        priority = Priority.HIGH,
        iconResId = R.drawable.ic_education,
        title = "Organize Study Desk",
        description = "",
        date = "2023-09-20"
    ),
    Task(
        priority = Priority.MEDIUM,
        iconResId = R.drawable.ic_education,
        title = "Organize Study Desk",
        description = "Review cell structure and functions for tomorrow...",
        date = "2023-09-20"
    ),
    Task(
        priority = Priority.HIGH,
        iconResId = R.drawable.ic_education,
        title = "Task 1,",
        description = "This is a task description",
        date = "2023-09-20"
    ),
    Task(
        priority = Priority.LOW,
        iconResId = R.drawable.ic_education,
        title = "Task 1,",
        description = "This is a task description",
        date = "2023-09-20"
    ),
    Task(
        priority = Priority.MEDIUM,
        iconResId = R.drawable.ic_education,
        title = "Task 1,",
        description = "This is a task description",
        date = "2023-09-20"
    ),
    Task(
        priority = Priority.LOW,
        iconResId = R.drawable.ic_education,
        title = "Task 1,",
        description = "This is a task description",
        date = "2023-09-20"
    ),
    Task(
        priority = Priority.HIGH,
        iconResId = R.drawable.ic_education,
        title = "Task 1,",
        description = "This is a task description",
        date = "2023-09-20"
    )
)