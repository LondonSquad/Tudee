package com.london.tudee.presentation.screens.tasks

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
import com.london.tudee.presentation.components.TopAppBar
import com.london.tudee.presentation.components.priority.Priority
import com.london.tudee.presentation.components.tabs.TabItem
import com.london.tudee.presentation.components.tabs.TudeeTabLayout
import com.london.tudee.presentation.components.task.Task
import com.london.tudee.presentation.components.task.TaskItem
import com.london.tudee.presentation.design_system.theme.ThemePreviews
import com.london.tudee.presentation.design_system.theme.TudeeTheme

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
                items(tasks.size) { index ->
                    TaskItem(
                        modifier = Modifier,
                        isSelected = true,
                        task = tasks[index]
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

val tasks = listOf(
    Task(
        priority = Priority.MEDIUM,
        iconResId = R.drawable.coding_icon,
        title = "Organize Study Desk",
        description = "Review cell structure and functions for tomorrow...",
        date = "2023-09-20"
    ),
    Task(
        priority = Priority.LOW,
        iconResId = R.drawable.coding_icon,
        title = "Organize Study Desk",
        description = "",
        date = "2023-09-20"
    ),
    Task(
        priority = Priority.HIGH,
        iconResId = R.drawable.coding_icon,
        title = "Organize Study Desk",
        description = "",
        date = "2023-09-20"
    ),
    Task(
        priority = Priority.MEDIUM,
        iconResId = R.drawable.coding_icon,
        title = "Organize Study Desk",
        description = "Review cell structure and functions for tomorrow...",
        date = "2023-09-20"
    ),
    Task(
        priority = Priority.HIGH,
        iconResId = R.drawable.coding_icon,
        title = "Task 1,",
        description = "This is a task description",
        date = "2023-09-20"
    ),
    Task(
        priority = Priority.LOW,
        iconResId = R.drawable.coding_icon,
        title = "Task 1,",
        description = "This is a task description",
        date = "2023-09-20"
    ),
    Task(
        priority = Priority.MEDIUM,
        iconResId = R.drawable.coding_icon,
        title = "Task 1,",
        description = "This is a task description",
        date = "2023-09-20"
    ),
    Task(
        priority = Priority.LOW,
        iconResId = R.drawable.coding_icon,
        title = "Task 1,",
        description = "This is a task description",
        date = "2023-09-20"
    ),
    Task(
        priority = Priority.HIGH,
        iconResId = R.drawable.coding_icon,
        title = "Task 1,",
        description = "This is a task description",
        date = "2023-09-20"
    )
)

@ThemePreviews
@Composable
private fun TudeeTaskPreview() {
    TudeeTheme {
        EditTaskDetails()
    }
}