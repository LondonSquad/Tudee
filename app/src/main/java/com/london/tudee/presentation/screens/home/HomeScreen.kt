package com.london.tudee.presentation.screens.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
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
import com.london.tudee.presentation.components.HomeTopBar
import com.london.tudee.presentation.components.NotificationSlider
import com.london.tudee.presentation.components.SnackBar
import com.london.tudee.presentation.components.StatusCard
import com.london.tudee.presentation.components.buttons.TudeeFloatingActionButton
import com.london.tudee.presentation.components.date.DateBadge
import com.london.tudee.presentation.components.date.DateBadgeStyleValues
import com.london.tudee.presentation.components.task.EmptyTasksScreen
import com.london.tudee.presentation.components.task.TaskItem
import com.london.tudee.presentation.design_system.theme.ThemePreviews
import com.london.tudee.presentation.design_system.theme.TudeeTheme
import com.london.tudee.presentation.screens.task.task_details.TaskDetailsBottomSheet
import com.london.tudee.presentation.screens.task.task_modify.TaskModifyBottomSheet
import com.london.tudee.presentation.screens.task.task_modify.TaskModifyUiState
import com.london.tudee.presentation.utils.HomeScreenUtils
import kotlinx.coroutines.delay
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel(),
    onArrowClicked: (Int) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    val taskUiState by viewModel.taskUiState.collectAsState()
    when {
        uiState.isLoading -> LoadingScreen(modifier = Modifier.fillMaxSize())
        uiState.errMessage != null -> ErrorScreen(modifier = Modifier.fillMaxSize())
        else -> HomeScreenContent(
            state = uiState,
            viewmodel = viewModel,
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
            text = stringResource(R.string.loading), modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Box(modifier) {
        Text(
            text = stringResource(R.string.there_was_an_unexpected_error),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun HomeScreenContent(
    state: HomeUiState,
    viewmodel: HomeViewModel,
    taskUiState: TaskModifyUiState,
    interactions: HomeInteractions,
    onArrowClicked: (Int) -> Unit
) {
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {

        TudeeFloatingActionButton(
            painter = painterResource(R.drawable.note_add),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .zIndex(if (taskUiState.showBottomSheet || state.isTaskDetailsBottomSheetVisible) 0f else 1f)
                .padding(bottom = 16.dp, end = 16.dp),
            contentDescription = "note icon",
            onClick = {
                interactions.showBottomSheet()
            },
            isEnabled = true,
        )

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TopAPPBar(viewmodel, state)

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
                        categoryIcons = taskUiState.categoryIcons,
                        onTaskClicked = interactions::showTaskDetailsBottomSheet,
                        loadTask = interactions::loadTask
                    )

                    Spacer(Modifier.height(24.dp))

                    ToDoSection(
                        toDoTasks = state.toDoTasks,
                        onTodoTasksArrowClicked = onArrowClicked,
                        categoryIcons = taskUiState.categoryIcons,
                        onTaskClicked = interactions::showTaskDetailsBottomSheet,
                        loadTask = interactions::loadTask
                    )

                    Spacer(Modifier.height(24.dp))

                    DoneSection(
                        doneTasks = state.doneTasks,
                        onDoneTasksArrowClicked = onArrowClicked,
                        categoryIcons = taskUiState.categoryIcons,
                        onTaskClicked = interactions::showTaskDetailsBottomSheet,
                        loadTask = interactions::loadTask
                    )
                }
            }
        }

        TaskDetailsBottomSheet(
            state.taskDetailBottomSheetUiState,
            showBottomSheet = state.isTaskDetailsBottomSheetVisible,
            onDismiss = interactions::hideTaskDetailsBottomSheet,
            onMoveClick = interactions::onClickMove,
            onEditClick = {
                interactions.hideTaskDetailsBottomSheet()
                val taskId = state.taskDetailBottomSheetUiState.task.id
                interactions.onEditTask(taskId)
            })

        TaskModifyBottomSheet(
            modifier = Modifier.zIndex(1f),
            screenContent = { },
            uiState = taskUiState,
            interactions = interactions
        )

        Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter
        ) {
            when (taskUiState.stateMessage) {
                R.string.add_task_successfully, R.string.edit_task_successfully -> {
                    SnackBar(
                        modifier = Modifier
                            .windowInsetsPadding(WindowInsets.statusBars)
                            .padding(top = 16.dp),
                        message = if (taskUiState.isEditMode) R.string.edit_task_successfully
                        else R.string.add_task_successfully,
                        iconPainter = painterResource(id = R.drawable.snack_bar_container),
                        iconTint = TudeeTheme.colors.greenAccent
                    )
                }

                R.string.some_error_happened -> {
                    SnackBar(
                        modifier = Modifier
                            .windowInsetsPadding(WindowInsets.statusBars)
                            .padding(top = 16.dp),
                        message = R.string.some_error_happened,
                        iconPainter = painterResource(id = R.drawable.snack_bar_error),
                        iconTint = TudeeTheme.colors.errorVariant,
                    )
                }
            }

            LaunchedEffect(taskUiState.stateMessage) {
                delay(3000)
                interactions.clearMessages()
            }
        }
    }
}

@Composable
private fun TopAPPBar(viewmodel: HomeViewModel, state: HomeUiState) {
    Box(
        modifier = Modifier
            .background(TudeeTheme.colors.primary)
            .padding(WindowInsets.statusBars.asPaddingValues())
            .height(72.dp)
            .fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically
        ) {
            HomeTopBar(
                isDarkMode = state.isDarkMode,
                onThemeChanged = { viewmodel.onThemeSwitched(it) },
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
                    .fillMaxHeight()
                    .align(Alignment.CenterHorizontally),
                dateText = dateOfToday,
                dateBadgeStyle = DateBadgeStyleValues(
                    shape = RectangleShape,
                    colors = CardDefaults.cardColors(containerColor = TudeeTheme.colors.surfaceHigh),
                    iconSize = 16.dp,
                    iconColor = TudeeTheme.colors.body,
                    iconTextSpacing = 8.dp,
                    textStyle = TudeeTheme.typography.labelMedium.copy(
                        fontSize = 14.sp,
                        lineHeight = 17.sp,
                        color = TudeeTheme.colors.body
                    )
                ),
                isVisible = true
            )

            NotificationSlider(
                note = null, notificationSliderUiState = getTaskStatus(
                    allTasks = numberOfAllTasks,
                    doneTasks = numberOfDoneTasks,
                    inProgressTasks = numberOfInProgressTasks,
                    toDoTasks = numberOfToDoTasks
                ), modifier = Modifier.padding(start = 6.dp)
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
    categoryIcons: List<String>,
    onTaskClicked: () -> Unit,
    loadTask: (Task) -> Unit
) {

    if (toDoTasks.isNotEmpty() && categoryIcons.isEmpty()) {
        return
    }

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
                .padding(vertical = 6.dp, horizontal = 8.dp), contentAlignment = Alignment.Center) {
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
        Log.d("MainActivity", "${categoryIcons.size}")
        items(toDoTasks.size) {
            TaskItem(
                modifier = Modifier
                    .width(320.dp)
                    .height(111.dp)
                    .clickable {
                        loadTask(toDoTasks[it])
                        onTaskClicked()
                    },
                isSelected = true,
                task = toDoTasks[it],
                hasDate = false,
                iconResId = categoryIcons[toDoTasks[it].categoryId - 1]
            )
        }
    }
}

@Composable
private fun InProgressSection(
    inProgressTasks: List<Task>,
    onInProgressTasksArrowClicked: (Int) -> Unit,
    categoryIcons: List<String>,
    onTaskClicked: () -> Unit,
    loadTask: (Task) -> Unit
) {

    if (inProgressTasks.isNotEmpty() && categoryIcons.isEmpty()) {
        return
    }

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
                .padding(vertical = 6.dp, horizontal = 8.dp), contentAlignment = Alignment.Center) {
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
                    .height(111.dp)
                    .clickable {
                        loadTask(inProgressTasks[it])
                        onTaskClicked()
                    },
                isSelected = true,
                task = inProgressTasks[it],
                hasDate = false,
                iconResId = categoryIcons[inProgressTasks[it].categoryId - 1]
            )
        }
    }
}

@Composable
private fun DoneSection(
    doneTasks: List<Task>,
    onDoneTasksArrowClicked: (Int) -> Unit,
    categoryIcons: List<String>,
    onTaskClicked: () -> Unit,
    loadTask: (Task) -> Unit
) {

    if (doneTasks.isNotEmpty() && categoryIcons.isEmpty()) {
        return
    }

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
                .padding(vertical = 6.dp, horizontal = 8.dp), contentAlignment = Alignment.Center) {
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
                    .height(111.dp)
                    .clickable {
                        loadTask(doneTasks[it])
                        onTaskClicked()
                    },
                isSelected = true,
                task = doneTasks[it],
                hasDate = false,
                iconResId = categoryIcons[doneTasks[it].categoryId - 1]
            )
        }
    }
}

@Composable
private fun getTaskStatus(
    allTasks: Int, doneTasks: Int, inProgressTasks: Int, toDoTasks: Int
): NotificationSliderUiState {
    when {
        doneTasks == 0 && inProgressTasks == 0 && toDoTasks == 0 -> {
            return NotificationSliderUiState(
                title = stringResource(R.string.Nothing_on_your_list),
                subtitle = stringResource(R.string.Fill_your_day_with_something_awesome_),
                emoji = R.drawable.bad_emoji,
                tudeePicture = R.drawable.tudee_warning
            )
        }

        inProgressTasks > doneTasks && inProgressTasks > toDoTasks -> {
            return NotificationSliderUiState(
                title = stringResource(R.string.Stay_working),
                subtitle = stringResource(R.string.task_progress, doneTasks, allTasks),
                emoji = R.drawable.okay_status,
                tudeePicture = R.drawable.tudee_warning
            )
        }

        doneTasks > inProgressTasks && doneTasks > toDoTasks && doneTasks == allTasks -> {
            return NotificationSliderUiState(
                title = stringResource(R.string.Tadaa),
                subtitle = stringResource(R.string.encouragement_message),
                emoji = R.drawable.good_emoji,
                tudeePicture = R.drawable.tudee_motivation
            )
        }

        doneTasks == 0 && inProgressTasks == 0 && toDoTasks == allTasks -> {
            return NotificationSliderUiState(
                title = stringResource(R.string.Zero_progress),
                subtitle = stringResource(R.string.blaming_message),
                emoji = R.drawable.poor_emoji,
                tudeePicture = R.drawable.tudee_complment
            )
        }

        else -> return NotificationSliderUiState(
            title = "",
            subtitle = "",
            emoji = R.drawable.bad_emoji,
            tudeePicture = R.drawable.tudee_warning
        )
    }
}

@ThemePreviews
@Composable
fun PreviewHomeScreen() {
    TudeeTheme {
        HomeScreen(
            onArrowClicked = {})
    }
}