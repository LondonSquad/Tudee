package com.london.tudee.presentation.screens.tasks

import androidx.compose.runtime.mutableStateListOf
import com.london.tudee.R
import com.london.tudee.domain.entities.Priority
import com.london.tudee.domain.entities.Task
import com.london.tudee.domain.entities.TaskStatus
import kotlinx.datetime.Instant

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
    Task(
        id = 1,
        priority = Priority.MEDIUM,
        categoryId = R.drawable.ic_education,
        title = "Organize Study Desk",
        description = "Review cell structure and functions for tomorrow...",
        taskStatus = TaskStatus.TODO,
        timeStamp = Instant.parse("2023-09-20T00:00:00Z")
    ),
    Task(
        id = 2,
        priority = Priority.LOW,
        categoryId = R.drawable.coding_icon,
        title = "Organize Study Desk",
        description = "",
        taskStatus = TaskStatus.TODO,
        timeStamp = Instant.parse("2023-09-20T00:00:00Z")
    ),
    Task(
        id = 3,
        priority = Priority.HIGH,
        categoryId = R.drawable.coding_icon,
        title = "Organize Study Desk",
        description = "Review cell structure and functions for tomorrow...",
        taskStatus = TaskStatus.TODO,
        timeStamp = Instant.parse("2023-09-20T00:00:00Z")
    ),
    Task(
        id = 4,
        priority = Priority.MEDIUM,
        categoryId = R.drawable.coding_icon,
        title = "Organize Study Desk",
        description = "Review cell structure and functions for tomorrow...",
        taskStatus = TaskStatus.TODO,
        timeStamp = Instant.parse("2023-09-20T00:00:00Z")
    ),
    Task(
        id = 5,
        priority = Priority.HIGH,
        categoryId = R.drawable.coding_icon,
        title = "Task 1,",
        description = "This is a task description",
        taskStatus = TaskStatus.TODO,
        timeStamp = Instant.parse("2023-09-20T00:00:00Z")
    ),
    Task(
        id = 5,
        priority = Priority.LOW,
        categoryId = R.drawable.coding_icon,
        title = "Task 1,",
        description = "This is a task description",
        taskStatus = TaskStatus.TODO,
        timeStamp = Instant.parse("2023-09-20T00:00:00Z")
    ),
    Task(
        id = 6,
        priority = Priority.MEDIUM,
        categoryId = R.drawable.coding_icon,
        title = "Task 1,",
        description = "This is a task description",
        taskStatus = TaskStatus.TODO,
        timeStamp = Instant.parse("2023-09-20T00:00:00Z")
    ),
    Task(
        id = 7,
        priority = Priority.LOW,
        categoryId = R.drawable.coding_icon,
        title = "Task 1,",
        description = "This is a task description",
        taskStatus = TaskStatus.TODO,
        timeStamp = Instant.parse("2023-09-20T00:00:00Z")
    ),
    Task(
        id = 8,
        priority = Priority.HIGH,
        categoryId = R.drawable.coding_icon,
        title = "Task 1,",
        description = "Review cell structure and functions for tomorrow...",
        taskStatus = TaskStatus.IN_PROGRESS,
        timeStamp = Instant.parse("2023-09-20T00:00:00Z")
    )
)