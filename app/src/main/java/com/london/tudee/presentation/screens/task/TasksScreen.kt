package com.london.tudee.presentation.screens.task

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.london.tudee.R
import com.london.tudee.domain.entities.Category
import com.london.tudee.domain.entities.Task
import com.london.tudee.presentation.components.EmptyTasksScreen
import com.london.tudee.presentation.components.SnackBar
import com.london.tudee.presentation.components.buttons.TudeeFloatingActionButton
import com.london.tudee.presentation.components.date.DateItem
import com.london.tudee.presentation.components.date.TudeeDatePicker
import com.london.tudee.presentation.components.tabs.TabItem
import com.london.tudee.presentation.components.tabs.TudeeTabLayoutWithPager
import com.london.tudee.presentation.components.task.SwipeToDeleteTask
import com.london.tudee.presentation.design_system.theme.ThemePreviews
import com.london.tudee.presentation.design_system.theme.TudeeTheme
import com.london.tudee.presentation.screens.task.task_delete.TaskDeletingBottomSheet
import com.london.tudee.presentation.utils.DateFormatter.toMonthShort
import com.london.tudee.presentation.utils.DateFormatter.toYear
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TasksScreen(
    initialTabIndex: Int = 0,
    viewModel: TasksScreenViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    var showDeleteSheet by remember { mutableStateOf(false) }
    var showDeleteSnackBar by remember { mutableStateOf(false) }

    TasksContent(
        initialTabIndex = initialTabIndex,
        date = uiState.date,
        inProgressTasksCount = uiState.inProgressTasks.size,
        toDoTasksCount = uiState.toDoTasks.size,
        doneTasksCount = uiState.doneTasks.size,
        inProgressTasks = uiState.inProgressTasks,
        toDoTasks = uiState.toDoTasks,
        doneTasks = uiState.doneTasks,
        days = uiState.days,
        categories = uiState.categories,
        onClickLeft = { viewModel.updateDateByAction(uiState.date, ArrowActions.Previous) },
        onClickRight = { viewModel.updateDateByAction(uiState.date, ArrowActions.Next) },
        onDateSelected = { viewModel.onDateSelected(it) },
        onDayClick = { viewModel.onDaySelected(it) },
        onDeleteTask = { task -> viewModel.showDeleteDialog(task.id) }
    )
    TaskDeletingBottomSheet(
        viewModel = viewModel,
        onTaskDeleted = {
            showDeleteSheet = false
            showDeleteSnackBar = true
            viewModel.initializeDoneTasks()
            viewModel.initializeToDoTasks()
            viewModel.initializeInProgressTasks()
        }
    )

    AnimatedVisibility(
        visible = showDeleteSnackBar,
        enter = fadeIn() + slideInVertically(initialOffsetY = { -100 }),
        exit = fadeOut() + slideOutVertically(targetOffsetY = { -100 })
    ) {
        SnackBar(
            modifier = Modifier
                .padding(top = 16.dp),
            message = R.string.delete_task_success,
            iconPainter = painterResource(id = R.drawable.snack_bar_container),
            iconTint = TudeeTheme.colors.greenAccent
        )
    }

    LaunchedEffect(showDeleteSnackBar) {
        if (showDeleteSnackBar) {
            delay(3000)
            showDeleteSnackBar = false
        }
    }
}

@Composable
fun TasksContent(
    initialTabIndex: Int,
    date: Long,
    inProgressTasksCount: Int,
    toDoTasksCount: Int,
    doneTasksCount: Int,
    inProgressTasks: List<Task>,
    toDoTasks: List<Task>,
    doneTasks: List<Task>,
    days: List<DaysOfMonth>,
    categories: List<Category>,
    onClickLeft: () -> Unit,
    onClickRight: () -> Unit,
    onDateSelected: (Long) -> Unit,
    onDayClick: (index: Int) -> Unit,
    onDeleteTask: (Task) -> Unit
) {

    var showDatePicker by remember { mutableStateOf(false) }
    var showBottomSheet by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = TudeeTheme.colors.surface,
        topBar = {
            TasksTopBar(
                modifier = Modifier
                    .background(color = TudeeTheme.colors.surfaceHigh)
            )
        },
        floatingActionButton = {
            TudeeFloatingActionButton(
                painter = painterResource(R.drawable.note_add),
                contentDescription = "note icon",
                onClick = {},
                isEnabled = true,
            )
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
    ) { innerPadding ->
        TudeeTabLayoutWithPager(
            modifier = Modifier.padding(innerPadding),
            initialTabIndex = initialTabIndex,
            tabs = listOf(
                TabItem(text = R.string.In_Progress, number = inProgressTasksCount),
                TabItem(text = R.string.To_Do, number = toDoTasksCount),
                TabItem(text = R.string.Done, number = doneTasksCount),

                ),
            tasksList = listOf(inProgressTasks, toDoTasks, doneTasks),
            headerContent = {
                DateSection(
                    modifier = Modifier.background(color = TudeeTheme.colors.surfaceHigh),
                    month = date.toMonthShort(),
                    year = date.toYear(),
                    onClickDate = { showDatePicker = true },
                    days = days,
                    onClickLeft = onClickLeft,
                    onClickRight = onClickRight,
                    onClickDay = onDayClick,
                )
            }
        ) { page, tasks ->
            if (tasks.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 121.dp),
                    contentAlignment = Alignment.TopCenter
                ) { EmptyTasksScreen() }
                return@TudeeTabLayoutWithPager
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                items(tasks.size) { index ->
                    val task = tasks[index]
                    val iconResId =
                        categories.find { it.id == task.categoryId }?.iconRes ?: ""
                    SwipeToDeleteTask(
                        modifier = Modifier,
                        task = task,
                        iconResId = iconResId,
                        onDeleteClick = { onDeleteTask(task) }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }

    if (showDatePicker)
        TudeeDatePicker(
            onDateSelected = { date ->
                date?.let { onDateSelected(it) }
                showDatePicker = false
            },
            onDismiss = { showDatePicker = false }
        )
}

@Composable
private fun TasksTopBar(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .padding(WindowInsets.statusBars.asPaddingValues())
            .height(64.dp)
            .fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.tasks),
                style = TudeeTheme.typography.titleLarge,
                color = TudeeTheme.colors.title,
            )
        }
    }
}


@Composable
fun DateSection(
    modifier: Modifier,
    month: String,
    year: String,
    onClickDate: () -> Unit,
    days: List<DaysOfMonth>,
    onClickLeft: () -> Unit,
    onClickRight: () -> Unit,
    onClickDay: (index: Int) -> Unit
) {
    DateSelector(
        modifier,
        month,
        year,
        onClickDate,
        onClickLeft,
        onClickRight
    )
    DaySelector(modifier, days, onClickDay)
}

@Composable
fun DateSelector(
    modifier: Modifier = Modifier,
    month: String,
    year: String,
    onClickDate: () -> Unit,
    onClickLeft: () -> Unit,
    onClickRight: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(TudeeTheme.shapes.circle)
                .border(
                    width = 1.dp,
                    color = TudeeTheme.colors.stroke,
                    shape = TudeeTheme.shapes.circle
                )
                .clickable { onClickLeft() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painterResource(R.drawable.left_arrow),
                contentDescription = null,
                tint = TudeeTheme.colors.body
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.clickable { onClickDate() }
        ) {
            Text(
                text = "$month, ",
                style = TudeeTheme.typography.labelMedium,
                color = TudeeTheme.colors.body
            )
            Text(
                text = year,
                style = TudeeTheme.typography.labelMedium,
                color = TudeeTheme.colors.body
            )
            Icon(
                painter = painterResource(R.drawable.down_arrow),
                contentDescription = null,
                tint = TudeeTheme.colors.body
            )
        }

        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(TudeeTheme.shapes.circle)
                .border(
                    width = 1.dp,
                    color = TudeeTheme.colors.stroke,
                    shape = TudeeTheme.shapes.circle
                )
                .clickable { onClickRight() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painterResource(R.drawable.right_arrow),
                contentDescription = null,
                tint = TudeeTheme.colors.body
            )
        }
    }
}

@Composable
fun DaySelector(
    modifier: Modifier = Modifier,
    days: List<DaysOfMonth>,
    onClickDay: (index: Int) -> Unit
) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(days) {
        days.indexOfFirst { it.isSelected }.takeIf { it != -1 }?.let { selectedIndex ->
            coroutineScope.launch {
                listState.animateScrollToItem(selectedIndex)
            }
        }
    }
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .height(65.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(start = 16.dp),
        state = listState
    ) {
        items(days.size) { index ->
            DateItem(
                dayOfMonth = days[index].dayOfMonth,
                dayOfWeek = days[index].dayOfWeek,
                isSelected = days[index].isSelected,
                onClick = {
                    onClickDay(index)
                }
            )
        }
    }
}

@ThemePreviews
@Composable
fun TasksScreenPreview() {
    TudeeTheme {
        TasksScreen()
    }
}