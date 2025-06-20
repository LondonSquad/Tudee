package com.london.tudee.presentation.screens.tasks

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.london.tudee.R
import com.london.tudee.presentation.components.date.DateItem
import com.london.tudee.presentation.components.date.TudeeDatePicker
import com.london.tudee.presentation.components.tabs.TabItem
import com.london.tudee.presentation.components.tabs.TudeeTabLayoutWithPager
import com.london.tudee.presentation.components.task.SwipeToDeleteTask
import com.london.tudee.presentation.design_system.theme.ThemePreviews
import com.london.tudee.presentation.design_system.theme.TudeeTheme
import org.koin.androidx.compose.koinViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TasksScreen(
    modifier: Modifier = Modifier,
    viewModel: TasksScreenViewModel = koinViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(TudeeTheme.colors.surface)
    ) {
        var showDatePicker by remember { mutableStateOf(false) }
        var selectedDate by remember { mutableStateOf<Long?>(null) }

        TasksTopAPPBar(
            modifier = Modifier
                .background(color = TudeeTheme.colors.surfaceHigh)
        )
        Column(
            modifier = Modifier
                .background(TudeeTheme.colors.surface)
        ) {
            DateSection(
                modifier = Modifier.background(
                    color = TudeeTheme.colors.surfaceHigh,
                ),
                month = currentMonth,
                year = currentYear,
                onClickLeft = {},
                onClickRight = {},
                onClickDate = { showDatePicker = true },
                dates = fakeDates
            )
            TudeeTabLayoutWithPager(
                tabs = listOf(
                    TabItem(text = R.string.In_Progress, number = uiState.inProgressTasks.size),
                    TabItem(text = R.string.To_Do, number = uiState.toDoTasks.size),
                    TabItem(text = R.string.Done, number = uiState.doneTasks.size),

                    )
            ) { page ->
                val tasks = when (page) {
                    0 -> uiState.inProgressTasks
                    1 -> uiState.toDoTasks
                    2 -> uiState.doneTasks
                    else -> emptyList()
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(TudeeTheme.colors.surface)
                ) {
                    if (tasks.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 121.dp),
                            contentAlignment = Alignment.TopCenter
                        ) {
                            EmptyTasksScreen()
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            items(tasks.size) { index ->
                                SwipeToDeleteTask(
                                    modifier = Modifier,
                                    task = tasks[index],
                                    onDeleteClick = {}
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                    }
                }

                if (showDatePicker) {
                    TudeeDatePicker(
                        onDateSelected = { date ->
                            selectedDate = date
                            showDatePicker = false
                        },
                        onDismiss = { showDatePicker = false }
                    )
                }
            }
        }
    }
}

@Composable
private fun TasksTopAPPBar(
    modifier: Modifier = Modifier
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
                text = "Tasks",
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
    onClickLeft: () -> Unit,
    onClickRight: () -> Unit,
    onClickDate: () -> Unit,
    dates: MutableList<DateItemClass>
) {
    DateSelector(modifier, month, year, onClickLeft, onClickRight, onClickDate)
    DayOfWeekSelector(modifier, dates)
}

@Composable
fun DateSelector(
    modifier: Modifier = Modifier,
    month: String,
    year: String,
    onClickLeft: () -> Unit,
    onClickRight: () -> Unit,
    onClickDate: () -> Unit
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
fun DayOfWeekSelector(
    modifier: Modifier = Modifier,
    dates: MutableList<DateItemClass>
) {
    val datesTest = remember { dates }
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .height(65.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(start = 16.dp),
        userScrollEnabled = true

    ) {
        items(dates.size) { index ->
            DateItem(
                dayOfMonth = datesTest[index].dayOfMonth,
                dayOfWeek = datesTest[index].dayOfWeek,
                isSelected = datesTest[index].isSelected,
            ) {

                datesTest.forEachIndexed { i, item ->
                    datesTest[i] = item.copy(isSelected = false)
                }
                datesTest[index] = datesTest[index].copy(isSelected = true)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@ThemePreviews
@Composable
fun TasksScreenPreview() {
    TudeeTheme {
        TasksScreen()
    }
}

