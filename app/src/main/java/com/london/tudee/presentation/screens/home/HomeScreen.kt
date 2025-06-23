package com.london.tudee.presentation.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.london.tudee.R
import com.london.tudee.domain.entities.Task
import com.london.tudee.domain.entities.TaskStatus
import com.london.tudee.presentation.base.BaseCreateTaskInteractions
import com.london.tudee.presentation.components.HomeTopBar
import com.london.tudee.presentation.components.SnackBar
import com.london.tudee.presentation.components.StatusCard
import com.london.tudee.presentation.components.TaskStatusSlider
import com.london.tudee.presentation.components.buttons.TudeeFloatingActionButton
import com.london.tudee.presentation.components.date.DateBadge
import com.london.tudee.presentation.components.task.TaskItem
import com.london.tudee.presentation.design_system.theme.ThemePreviews
import com.london.tudee.presentation.design_system.theme.TudeeTheme
import com.london.tudee.presentation.screens.task.add_edit_task_bottom_sheet.AddOrEditTaskBottomSheet
import com.london.tudee.presentation.screens.task.add_edit_task_bottom_sheet.AddOrEditTaskUiState
import com.london.tudee.presentation.screens.tasks.EmptyTasksScreen
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel(),
    onArrowClicked: (Int) -> Unit,
) {
    val homeUiState by viewModel.uiState.collectAsState()
    val taskUiState by viewModel.taskUiState.collectAsState()
    when {
        homeUiState.isLoading -> LoadingScreen(modifier = Modifier.fillMaxSize())
        homeUiState.errMessage != null -> ErrorScreen(modifier = Modifier.fillMaxSize())
        else -> HomeScreenContent(
            state = homeUiState,
            interactions = viewModel,
            onArrowClicked = onArrowClicked,
            taskUiState = taskUiState,
        )
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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreenContent(
    state: HomeUiState,
    taskUiState: AddOrEditTaskUiState,
    interactions: BaseCreateTaskInteractions,
    onArrowClicked: (Int) -> Unit
) {
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {

        TudeeFloatingActionButton(
            painter = painterResource(R.drawable.note_add),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .zIndex(if (taskUiState.showBottomSheet) 0f else 1f)
                .padding(bottom = 84.dp, end = 12.dp),
            contentDescription = "note icon",
            onClick = {
                interactions.showBottomSheet()
            },
            isEnabled = true,
        )

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TopAPPBar()

            Column(
                modifier = Modifier
                    .weight(1f)
                    .background(TudeeTheme.colors.surface)
                    .verticalScroll(rememberScrollState())
            ) {

                OverLayerBox(
                    numberOfAllTasks = state.allTasks.size,
                    numberOfDoneTasks = state.doneTasks.size,
                    numberOfInProgressTasks = state.inProgressTasks.size,
                    numberOfToDoTasks = state.toDoTasks.size,
                    dateOfToday = "${stringResource(R.string.today)} ${
                        HomeScreenUtils.customDateFormatter(
                            context
                        )
                    }"
                )

                if (state.allTasks.isEmpty()) {
                    EmptyTasksScreen()
                } else {
                    InProgressSection(
                        inProgressTasks = state.inProgressTasks,
                        onInProgressTasksArrowClicked = onArrowClicked,
                        categoryIcons = taskUiState.categoryIcons
                    )

                    Spacer(Modifier.height(24.dp))

                    ToDoSection(
                        toDoTasks = state.toDoTasks,
                        onTodoTasksArrowClicked = onArrowClicked,
                        categoryIcons = taskUiState.categoryIcons
                    )

                    Spacer(Modifier.height(24.dp))

                    DoneSection(
                        doneTasks = state.doneTasks,
                        onDoneTasksArrowClicked = onArrowClicked,
                        categoryIcons = taskUiState.categoryIcons
                    )
                }
            }
        }

        AddOrEditTaskBottomSheet(
            modifier = Modifier.zIndex(1f),
            title = R.string.add_new_task,
            buttonText = R.string.add,
            screenContent = { },
            uiState = taskUiState,
            interactions = interactions
        )

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            when {
                taskUiState.successMessage != null -> {
                    SnackBar(
                        modifier = Modifier.offset(y = 56.dp),
                        message = if (taskUiState.isEditMode)
                            R.string.edit_task_successfully
                        else
                            R.string.add_task_successfully,
                        iconPainter = painterResource(id = R.drawable.snack_bar_container),
                        iconTint = TudeeTheme.colors.greenAccent
                    )
                }

                taskUiState.errorMessage != null -> {
                    SnackBar(
                        modifier = Modifier.offset(y = 56.dp),
                        message = R.string.some_error_happened,
                        iconPainter = painterResource(id = R.drawable.snack_bar_error),
                        iconTint = TudeeTheme.colors.errorVariant,
                    )
                }
            }

            LaunchedEffect(taskUiState.successMessage, taskUiState.errorMessage) {
                delay(3000)
                interactions.clearMessages()
            }
        }
    }
}

@Composable
private fun TopAPPBar() {
    Box(
        modifier = Modifier
            .background(TudeeTheme.colors.primary)
            .padding(WindowInsets.statusBars.asPaddingValues())
            .height(72.dp)
            .fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val systemDarkTheme = isSystemInDarkTheme()
            var isDark by remember { mutableStateOf(systemDarkTheme) }
            HomeTopBar(
                isDarkMode = isDark,
                onCheckedChange = { isDark = it },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
private fun OverLayerBox(
    numberOfAllTasks: Int,
    numberOfDoneTasks: Int,
    numberOfInProgressTasks: Int,
    numberOfToDoTasks: Int,
    dateOfToday: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
                .background(TudeeTheme.colors.primary)
                .align(Alignment.TopCenter)
                .zIndex(0f)
        )

        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .clip(shape = TudeeTheme.shapes.small)
                .background(TudeeTheme.colors.surfaceHigh)
                .zIndex(1f)
                .align(Alignment.TopCenter)
        ) {
            DateBadge(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .height(17.dp)
                    .align(Alignment.CenterHorizontally),
                shape = RectangleShape,
                colors = CardDefaults.cardColors(containerColor = TudeeTheme.colors.surfaceHigh),
                dateText = dateOfToday,
                iconSize = 16.dp,
                textSize = 14.sp,
                textStyle = TudeeTheme.typography.labelMedium,
                lineHeight = 16.sp,
                iconColor = TudeeTheme.colors.body,
                textColor = TudeeTheme.colors.body,
                contentPadding = PaddingValues(vertical = 0.dp),
                isVisible = true
            )

            TaskStatusSlider(
                note = null,
                taskStatusUiState = getTaskStatus(
                    allTasks = numberOfAllTasks,
                    doneTasks = numberOfDoneTasks,
                    inProgressTasks = numberOfInProgressTasks,
                    toDoTasks = numberOfToDoTasks
                ),
                modifier = Modifier.padding(start = 6.dp)
            )

            Text(
                text = stringResource(R.string.Overview),
                style = TudeeTheme.typography.titleLarge,
                color = TudeeTheme.colors.title,
                modifier = Modifier.padding(horizontal = 12.dp)
            )

            Spacer(Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .padding(bottom = 12.dp)
                    .fillMaxWidth()
            ) {
                StatusCard(
                    backgroundColor = TudeeTheme.colors.greenAccent,
                    statusIcon = R.drawable.file_verified,
                    tasksNumber = numberOfDoneTasks,
                    taskStatusName = R.string.Done,
                    modifier = Modifier.weight(1f)
                )

                StatusCard(
                    backgroundColor = TudeeTheme.colors.yellowAccent,
                    statusIcon = R.drawable.file_pin,
                    tasksNumber = numberOfInProgressTasks,
                    taskStatusName = R.string.In_Progress,
                    modifier = Modifier.weight(1f)
                )

                StatusCard(
                    backgroundColor = TudeeTheme.colors.purpleAccent,
                    statusIcon = R.drawable.file_unknown,
                    tasksNumber = numberOfToDoTasks,
                    taskStatusName = R.string.To_Do,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun ToDoSection(
    toDoTasks: List<Task>,
    onTodoTasksArrowClicked: (Int) -> Unit,
    categoryIcons: List<String>
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(bottom = 8.dp)
    ) {
        Text(
            text = stringResource(R.string.To_Do),
            style = TudeeTheme.typography.titleLarge,
            color = TudeeTheme.colors.title
        )

        Box(
            Modifier
                .background(
                    color = TudeeTheme.colors.surfaceHigh,
                    shape = TudeeTheme.shapes.circle,
                )
                .clip(shape = TudeeTheme.shapes.circle)
                .clickable {
                    onTodoTasksArrowClicked(TaskStatus.TODO.index)
                }
                .padding(vertical = 6.dp, horizontal = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(2.dp),
            ) {
                Text(
                    text = "${toDoTasks.size}",
                    style = TudeeTheme.typography.labelSmall,
                    color = TudeeTheme.colors.body
                )

                Icon(
                    painter = painterResource(R.drawable.left_arrow_icon),
                    contentDescription = null,
                )
            }
        }
    }

    val itemHeight = 111.dp
    val verticalSpacing = 8.dp
    val maxRows = 2
    val actualRows = if (toDoTasks.isEmpty()) 0 else minOf(maxRows, toDoTasks.size)
    val contentHeight = if (actualRows == 0) 0.dp else {
        (itemHeight * actualRows) + (verticalSpacing * maxOf(0, actualRows - 1))
    }
    val finalHeight = minOf(contentHeight, 230.dp)

    LazyHorizontalGrid(
        contentPadding = PaddingValues(horizontal = 16.dp),
        rows = GridCells.Fixed(actualRows.coerceAtLeast(1)),
        modifier = Modifier.height(finalHeight),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(toDoTasks.size) {
            TaskItem(
                modifier = Modifier
                    .width(320.dp)
                    .height(111.dp),
                isSelected = true,
                task = toDoTasks[it],
                hasDate = false,
                iconResId = categoryIcons[it]
            )
        }
    }
}

@Composable
private fun InProgressSection(
    inProgressTasks: List<Task>,
    onInProgressTasksArrowClicked: (Int) -> Unit,
    categoryIcons: List<String>
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(bottom = 8.dp)
    ) {
        Text(
            text = stringResource(R.string.In_Progress),
            style = TudeeTheme.typography.titleLarge,
            color = TudeeTheme.colors.title
        )

        Box(
            Modifier
                .background(
                    color = TudeeTheme.colors.surfaceHigh,
                    shape = TudeeTheme.shapes.circle,
                )
                .clip(shape = TudeeTheme.shapes.circle)
                .clickable {
                    onInProgressTasksArrowClicked(TaskStatus.IN_PROGRESS.index)
                }
                .padding(vertical = 6.dp, horizontal = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(2.dp),

                ) {
                Text(
                    text = "${inProgressTasks.size}",
                    style = TudeeTheme.typography.labelSmall,
                    color = TudeeTheme.colors.body
                )

                Icon(
                    painter = painterResource(R.drawable.left_arrow_icon),
                    contentDescription = null,
                )
            }
        }
    }

    val itemHeight = 111.dp
    val verticalSpacing = 8.dp
    val maxRows = 2
    val actualRows = if (inProgressTasks.isEmpty()) 0 else minOf(maxRows, inProgressTasks.size)
    val contentHeight = if (actualRows == 0) 0.dp else {
        (itemHeight * actualRows) + (verticalSpacing * maxOf(0, actualRows - 1))
    }
    val finalHeight = minOf(contentHeight, 230.dp)

    LazyHorizontalGrid(
        contentPadding = PaddingValues(horizontal = 16.dp),
        rows = GridCells.Fixed(actualRows.coerceAtLeast(1)),
        modifier = Modifier.height(finalHeight),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(inProgressTasks.size) {
            TaskItem(
                modifier = Modifier
                    .width(320.dp)
                    .height(111.dp),
                isSelected = true,
                task = inProgressTasks[it],
                hasDate = false,
                iconResId = categoryIcons[it]
            )
        }
    }
}

@Composable
private fun DoneSection(
    doneTasks: List<Task>,
    onDoneTasksArrowClicked: (Int) -> Unit,
    categoryIcons: List<String>
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(bottom = 8.dp)
    ) {
        Text(
            text = stringResource(R.string.Done),
            style = TudeeTheme.typography.titleLarge,
            color = TudeeTheme.colors.title
        )

        Box(
            Modifier
                .background(
                    color = TudeeTheme.colors.surfaceHigh,
                    shape = TudeeTheme.shapes.circle,
                )
                .clip(shape = TudeeTheme.shapes.circle)
                .clickable {
                    onDoneTasksArrowClicked(TaskStatus.DONE.index)
                }
                .padding(vertical = 6.dp, horizontal = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(2.dp),
            ) {
                Text(
                    text = "${doneTasks.size}",
                    style = TudeeTheme.typography.labelSmall,
                    color = TudeeTheme.colors.body
                )

                Icon(
                    painter = painterResource(R.drawable.left_arrow_icon),
                    contentDescription = null,
                )
            }
        }
    }

    val itemHeight = 111.dp
    val verticalSpacing = 8.dp
    val maxRows = 2
    val actualRows = if (doneTasks.isEmpty()) 0 else minOf(maxRows, doneTasks.size)
    val contentHeight = if (actualRows == 0) 0.dp else {
        (itemHeight * actualRows) + (verticalSpacing * maxOf(0, actualRows - 1))
    }
    val finalHeight = minOf(contentHeight, 230.dp)

    LazyHorizontalGrid(
        contentPadding = PaddingValues(horizontal = 16.dp),
        rows = GridCells.Fixed(actualRows.coerceAtLeast(1)),
        modifier = Modifier.height(finalHeight),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(doneTasks.size) {
            TaskItem(
                modifier = Modifier
                    .width(320.dp)
                    .height(111.dp),
                isSelected = true,
                task = doneTasks[it],
                hasDate = false,
                iconResId = categoryIcons[it]
            )
        }
    }
}

@ThemePreviews
@Composable
fun PreviewHomeScreen() {
    TudeeTheme {
        HomeScreen(
            onArrowClicked = {}
        )
    }
}