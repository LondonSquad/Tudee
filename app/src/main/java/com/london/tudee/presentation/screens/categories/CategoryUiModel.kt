package com.london.tudee.presentation.screens.categories

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.london.tudee.R
import com.london.tudee.domain.entities.Priority
import com.london.tudee.domain.entities.Task
import com.london.tudee.domain.entities.TaskStatus
import com.london.tudee.presentation.design_system.color.TudeeColors
import com.london.tudee.presentation.design_system.theme.TudeeTheme
import com.london.tudee.presentation.design_system.theme.TudeeTheme.colors
import kotlinx.datetime.Instant

data class CategoryUiModel(
    val id: Int,
    @DrawableRes val iconRes: Int,
    @StringRes val title: Int,
    val count: Int,
    val isSelected: Boolean = false,
    val tasks: List<Task>
)

fun sampleTasks(categoryId: Int): List<Task> {
    return listOf(
        Task(
            id = 1,
            title = "Task $categoryId - To Do",
            description = "This is a sample TO_DO task",
            taskStatus = TaskStatus.TODO,
            priority = Priority.HIGH,
            categoryId = categoryId,
            timeStamp  = Instant.parse("2023-09-20T00:00:00Z")
        ),
        Task(
            id = 2,
            title = "Task $categoryId - In Progress",
            description = "This is a sample IN_PROGRESS task",
            taskStatus = TaskStatus.IN_PROGRESS,
            priority = Priority.MEDIUM,
            categoryId = categoryId,
            timeStamp = Instant.parse("2023-09-20T00:00:00Z")
        ),
        Task(
            id = 3,
            title = "Task $categoryId - Done",
            description = "This is a sample DONE task",
            taskStatus = TaskStatus.DONE,
            priority = Priority.LOW,
            categoryId = categoryId,
            timeStamp = Instant.parse("2023-09-20T00:00:00Z")
        )
    )
}

fun getSampleCategories(colors: TudeeColors): List<CategoryUiModel> {
    return listOf(
        CategoryUiModel(
            id = 1,
            iconRes = R.drawable.ic_education,
            title = R.string.category_education,
            count = 3,
            tasks = sampleTasks(categoryId = 1)
        ),
        CategoryUiModel(
            id = 2,
            iconRes = R.drawable.ic_shopping,
            title = R.string.category_shopping,
            count = 3,
            tasks = sampleTasks(categoryId = 2)
        ),
        CategoryUiModel(
            id = 3,
            iconRes = R.drawable.ic_medical,
            title = R.string.category_medical,
            count = 3,
            tasks = sampleTasks(categoryId = 3)
        ),
        CategoryUiModel(
            id = 4,
            iconRes = R.drawable.ic_gym,
            title = R.string.category_gym,
            count = 3,
            tasks = sampleTasks(categoryId = 4)
        ),
        CategoryUiModel(
            id = 5,
            iconRes = R.drawable.ic_entertainment,
            title = R.string.category_entertainment,
            count = 3,
            tasks = sampleTasks(categoryId = 5)
        ),
        CategoryUiModel(
            id = 6,
            iconRes = R.drawable.ic_cooking,
            title = R.string.category_cooking,
            count = 3,
            tasks = sampleTasks(categoryId = 6)
        ),
        CategoryUiModel(
            id = 7,
            iconRes = R.drawable.ic_family,
            title = R.string.category_family_friend,
            count = 3,
            tasks = sampleTasks(categoryId = 7)
        ),
        CategoryUiModel(
            id = 8,
            iconRes = R.drawable.ic_travel,
            title = R.string.category_traveling,
            count = 3,
            tasks = sampleTasks(categoryId = 8)
        ),
        CategoryUiModel(
            id = 9,
            iconRes = R.drawable.ic_agriculture,
            title = R.string.category_agriculture,
            count = 3,
            tasks = sampleTasks(categoryId = 9)
        ),
        CategoryUiModel(
            id = 10,
            iconRes = R.drawable.ic_coding,
            title = R.string.category_coding,
            count = 3,
            tasks = sampleTasks(categoryId = 10)
        ),
        CategoryUiModel(
            id = 11,
            iconRes = R.drawable.ic_adoration,
            title = R.string.category_adoration,
            count = 3,
            tasks = sampleTasks(categoryId = 11)
        ),
        CategoryUiModel(
            id = 12,
            iconRes = R.drawable.ic_bug_fix,
            title = R.string.category_fixing_bugs,
            count = 3,
            tasks = sampleTasks(categoryId = 12)
        ),
        CategoryUiModel(
            id = 13,
            iconRes = R.drawable.ic_cleaning,
            title = R.string.category_cleaning,
            count = 3,
            tasks = sampleTasks(categoryId = 13)
        ),
        CategoryUiModel(
            id = 14,
            iconRes = R.drawable.ic_work,
            title = R.string.category_work,
            count = 3,
            tasks = sampleTasks(categoryId = 14)
        ),
        CategoryUiModel(
            id = 15,
            iconRes = R.drawable.ic_budgeting,
            title = R.string.category_budgeting,
            count = 3,
            tasks = sampleTasks(categoryId = 15)
        ),
        CategoryUiModel(
            id = 16,
            iconRes = R.drawable.ic_self_care,
            title = R.string.category_self_care,
            count = 3,
            tasks = sampleTasks(categoryId = 16)
        ),
        CategoryUiModel(
            id = 17,
            iconRes = R.drawable.ic_event,
            title = R.string.category_event,
            count = 3,
            tasks = sampleTasks(categoryId = 17)
        )
    )
}