@file:JvmName("TaskDetailsScreenKt")

package com.london.tudee.presentation.screens.task.view_tasks

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.london.tudee.R
import com.london.tudee.domain.entities.Priority
import com.london.tudee.domain.entities.Task
import com.london.tudee.domain.entities.TaskStatus
import com.london.tudee.domain.services.CategoryService
import com.london.tudee.presentation.components.TaskStatusSlider
import com.london.tudee.presentation.components.TopAppBar
import com.london.tudee.presentation.components.tabs.TabItem
import com.london.tudee.presentation.components.tabs.TudeeTabLayout
import com.london.tudee.presentation.components.task.TaskItem
import com.london.tudee.presentation.design_system.theme.ThemePreviews
import com.london.tudee.presentation.design_system.theme.TudeeTheme
import kotlinx.datetime.Instant

@Composable
fun EditTaskDetails(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .background(TudeeTheme.colors.surface)
    ) {
        TopAppBar(
            title = R.string.coding,
            onBackClick = {},
            onClickAction = {},
            modifier = Modifier,
            navigationIcon = {
                IconButton(
                    onClick = it,
                    modifier = Modifier
                        .then(
                            if (LocalLayoutDirection.current == LayoutDirection.Rtl)
                                Modifier.rotate(180f)
                            else Modifier
                        )
                        .border(
                            1.dp,
                            TudeeTheme.colors.stroke,
                            TudeeTheme.shapes.circle
                        )
                ) {
                    Icon(
                        painter = painterResource(R.drawable.back_arrow),
                        contentDescription = stringResource(R.string.back_arrow),
                        tint = TudeeTheme.colors.body
                    )
                }
            },
            actions = {
                IconButton(
                    onClick = it,
                    modifier = Modifier
                        .then(
                            if (LocalLayoutDirection.current == LayoutDirection.Rtl)
                                Modifier.rotate(180f)
                            else Modifier
                        )
                        .border(
                            1.dp,
                            TudeeTheme.colors.stroke,
                            TudeeTheme.shapes.circle
                        )
                ) {
                    Icon(
                        painter = painterResource(R.drawable.edit_icon),
                        contentDescription = "Edit Icon",
                        tint = TudeeTheme.colors.body
                    )
                }
            }
        )

        TudeeTabLayout(
            tabs = listOf(
                TabItem(text = R.string.In_Progress, number = 14),
                TabItem(text = R.string.Done, number = 10),
                TabItem(text = R.string.To_Do, number = 2),
            ),
            selectedIndex = 0,
            onTabSelected = {},
            modifier = Modifier.fillMaxWidth()
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(TudeeTheme.colors.stroke)
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(TudeeTheme.colors.surface)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                items(taskUiStates.size) { index ->
                    TaskItem(
                        modifier = Modifier,
                        isSelected = true,
                        task = taskUiStates[index],
                        hasDate = true
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

val taskUiStates = listOf(
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

@ThemePreviews
@Composable
private fun TudeeTaskPreview() {
    TudeeTheme {
        EditTaskDetails()
    }
}