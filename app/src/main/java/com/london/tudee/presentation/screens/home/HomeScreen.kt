package com.london.tudee.presentation.screens.home

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.london.tudee.R
import com.london.tudee.presentation.components.HomeTopBar
import com.london.tudee.presentation.components.StatusCard
import com.london.tudee.presentation.components.TaskStatusSlider
import com.london.tudee.presentation.components.buttons.TudeeFloatingActionButton
import com.london.tudee.presentation.components.date.DateBadge
import com.london.tudee.presentation.components.task.TaskItem
import com.london.tudee.presentation.design_system.theme.ThemePreviews
import com.london.tudee.presentation.design_system.theme.TudeeTheme
import com.london.tudee.presentation.model.TaskUiState
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()
    when {
        uiState.isLoading -> LoadingScreen(modifier = Modifier.fillMaxSize())
        uiState.errMessage != null -> ErrorScreen(modifier = Modifier.fillMaxSize())
        else -> HomeScreenContent(uiState)
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
    state: HomeUiState
) {
    Scaffold(
        floatingActionButton = {
            TudeeFloatingActionButton(
                painter = painterResource(R.drawable.note_add),
                contentDescription = "note icon",
                onClick = { },
                isEnabled = true
            )
        }
    ) {
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
                    dateOfToday = "today, ${HomeScreenUtils.customDateFormatter()}"
                )

                InProgressSection(
                    inProgressTasks = state.inProgressTasks
                )

                Spacer(Modifier.height(24.dp))

                ToDoSection(
                    toDoTasks = state.toDoTasks
                )

                Spacer(Modifier.height(24.dp))

                DoneSection(
                    doneTasks = state.doneTasks
                )
            }
        }
    }
}

@Composable
private fun TopAPPBar() {
    Box(
        modifier = Modifier
            .background(TudeeTheme.colors.primary)
            .padding(horizontal = 16.dp)
            .padding(WindowInsets.statusBars.asPaddingValues())
            .height(72.dp)
            .fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val isDark = remember { mutableStateOf(false) }
            HomeTopBar(
                isDarkMode = isDark.value,
                onCheckedChange = { isDark.value = it },
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
    toDoTasks: List<TaskUiState>
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
                .padding(vertical = 6.dp, horizontal = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(2.dp)
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
                taskUiState = toDoTasks[it],
                hasDate = false,
            )
        }
    }
}

@Composable
private fun InProgressSection(
    inProgressTasks: List<TaskUiState>
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
                .padding(vertical = 6.dp, horizontal = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(2.dp)
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
                taskUiState = inProgressTasks[it],
                hasDate = false
            )
        }
    }
}

@Composable
private fun DoneSection(
    doneTasks: List<TaskUiState>
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
                .padding(vertical = 6.dp, horizontal = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(2.dp)
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
                taskUiState = doneTasks[it],
                hasDate = false
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@ThemePreviews
@Composable
fun PreviewHomeScreen() {
    TudeeTheme {
        HomeScreen()
    }
}