@file:JvmName("TaskDetailsScreenKt")

package com.london.tudee.presentation.screens.task.view_tasks

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
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
import com.london.tudee.presentation.components.tabs.TudeeTabLayoutWithPager
import com.london.tudee.presentation.components.task.TaskItem
import com.london.tudee.presentation.design_system.theme.ThemePreviews
import com.london.tudee.presentation.design_system.theme.TudeeTheme
import org.koin.androidx.compose.koinViewModel
import kotlinx.datetime.Instant

@Composable
fun EditTaskDetails(
    viewModel: EditTaskViewModel = koinViewModel(),
) {

    val uiState by viewModel.uiState.collectAsState()
    when {
        uiState.isLoading -> LoadingScreen(modifier = Modifier.fillMaxSize())
        uiState.errMessage != null -> ErrorScreen(modifier = Modifier.fillMaxSize())
        else -> EditTaskDetailsContent(state = uiState)
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(modifier) {
        Text(
            text = "Loading...",
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Box(modifier) {
        Text(
            text = "There was an unexpected error",
            modifier = Modifier.align(Alignment.Center)
        )
    }
}


@Composable
fun EditTaskDetailsContent(
    state: EditTaskDetailsState,
) {
    Column(
        modifier = Modifier
            .background(TudeeTheme.colors.surface)
    ) {

        TopAPPBar()

        TudeeTabLayoutWithPager(
            tabs = listOf(
                TabItem(text = R.string.In_Progress, number = state.inProgressTasks.size),
                TabItem(text = R.string.To_Do, number = state.toDoTasks.size),
                TabItem(
                    text = R.string.Done, number = state.doneTasks.size
                ),

                ),
        )
        { page ->
            val tasks = when (page) {
                0 -> state.inProgressTasks
                1 -> state.toDoTasks
                else -> state.doneTasks
            }
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
                            task = state.allTasks[index],
                            hasDate = true
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun TopAPPBar() {
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
=======
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

@ThemePreviews
@Composable
private fun TudeeTaskPreview() {
    TudeeTheme {
        EditTaskDetails()
    }
}

