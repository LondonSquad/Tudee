package com.london.tudee.presentation.screens.tasks

import androidx.compose.runtime.mutableStateListOf
import com.london.tudee.R
import com.london.tudee.domain.entities.Priority
import com.london.tudee.presentation.model.TaskUiState

data class DateItemClass(
    val dayOfMonth: String, val dayOfWeek: String, var isSelected: Boolean
)

val currentYear = "2025"
val currentMonth = "Jun"

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
    TaskUiState(
        id = 1,
        priority = Priority.MEDIUM,
        iconResId = R.drawable.ic_education,
        title = "Organize Study Desk",
        description = "Review cell structure and functions for tomorrow...",
        date = "2023-09-20"
    ),
    TaskUiState(
        id = 2,
        priority = Priority.LOW,
        iconResId = R.drawable.ic_education,
        title = "Organize Study Desk",
        description = "",
        date = "2023-09-20"
    ),
    TaskUiState(
        id = 3,
        priority = Priority.HIGH,
        iconResId = R.drawable.ic_education,
        title = "Organize Study Desk",
        description = "",
        date = "2023-09-20"
    ),
    TaskUiState(
        id = 4,
        priority = Priority.MEDIUM,
        iconResId = R.drawable.ic_education,
        title = "Organize Study Desk",
        description = "Review cell structure and functions for tomorrow...",
        date = "2023-09-20"
    ),
    TaskUiState(
        id = 5,
        priority = Priority.HIGH,
        iconResId = R.drawable.ic_education,
        title = "Task 1,",
        description = "This is a task description",
        date = "2023-09-20"
    ),
    TaskUiState(
        id = 6,
        priority = Priority.LOW,
        iconResId = R.drawable.ic_education,
        title = "Task 1,",
        description = "This is a task description",
        date = "2023-09-20"
    ),
    TaskUiState(
        id = 7,
        priority = Priority.MEDIUM,
        iconResId = R.drawable.ic_education,
        title = "Task 1,",
        description = "This is a task description",
        date = "2023-09-20"
    ),
    TaskUiState(
        id = 8,
        priority = Priority.LOW,
        iconResId = R.drawable.ic_education,
        title = "Task 1,",
        description = "This is a task description",
        date = "2023-09-20"
    ),
    TaskUiState(
        id = 9,
        priority = Priority.HIGH,
        iconResId = R.drawable.ic_education,
        title = "Task 1,",
        description = "This is a task description",
        date = "2023-09-20"
    )
)