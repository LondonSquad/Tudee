@file:JvmName("TaskDetailsScreenKt")

package com.london.tudee.presentation.screens.task.view_tasks

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
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
import com.london.tudee.presentation.components.TopAppBar
import com.london.tudee.presentation.components.tabs.TabItem
import com.london.tudee.presentation.components.tabs.TudeeTabLayoutWithPager
import com.london.tudee.presentation.components.task.TaskItem
import com.london.tudee.presentation.design_system.theme.ThemePreviews
import com.london.tudee.presentation.design_system.theme.TudeeTheme
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CategoryDetailsScreen(
    categoryId: Int,
    onBackClick: () -> Unit,
    viewModel: CategoryDetailsViewModel = koinViewModel(),
) {

    val uiState by viewModel.uiState.collectAsState()
    when {
        uiState.isLoading -> LoadingScreen(modifier = Modifier.fillMaxSize())
        uiState.errMessage != null -> ErrorScreen(modifier = Modifier.fillMaxSize())
        else -> CategoryDetailsContent(
            state = uiState,
            onBackClick = onBackClick,
            categoryId = categoryId
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

@Composable
fun CategoryDetailsContent(
    categoryId: Int,
    state: CategoryDetailsState,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .background(TudeeTheme.colors.surface)
            .padding(WindowInsets.statusBars.asPaddingValues())
    ) {

        TopAPPBar(
            onBackClick = onBackClick,
            state = state,
            categoryId = categoryId
        )

        TasksPagerSection(state = state)
    }
}

@Composable
fun TasksPagerSection(state: CategoryDetailsState) {
    TudeeTabLayoutWithPager(
        tabs = listOf(
            TabItem(text = R.string.In_Progress, number = state.inProgressTasks.size),
            TabItem(text = R.string.To_Do, number = state.toDoTasks.size),
            TabItem(text = R.string.Done, number = state.doneTasks.size),
        ),
        tasksList = listOf(state.inProgressTasks, state.toDoTasks, state.doneTasks)
    )
    { page, tasks ->

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
                        task = tasks[index],
                        hasDate = true
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
private fun TopAPPBar(onBackClick: () -> Unit, state: CategoryDetailsState, categoryId: Int) {
    TopAppBar(
        title = state.category.title,
        onBackClick = onBackClick,
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
                if (!state.category.isDefault) {
                    Icon(
                        painter = painterResource(R.drawable.edit_icon),
                        contentDescription = "Edit Icon",
                        tint = TudeeTheme.colors.body
                    )
                }

            }
        }
    )
}

@ThemePreviews
@Composable
private fun TudeeTaskPreview() {
    TudeeTheme {
        CategoryDetailsScreen(categoryId = 1, onBackClick = {})
    }
}

