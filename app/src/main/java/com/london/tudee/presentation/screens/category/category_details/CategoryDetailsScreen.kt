@file:JvmName("TaskDetailsScreenKt")

package com.london.tudee.presentation.screens.category.category_details

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.london.tudee.presentation.components.SnackBar
import com.london.tudee.presentation.components.TopAppBar
import com.london.tudee.presentation.components.tabs.TabItem
import com.london.tudee.presentation.components.tabs.TudeeTabLayoutWithPager
import com.london.tudee.presentation.components.task.TaskItem
import com.london.tudee.presentation.design_system.theme.ThemePreviews
import com.london.tudee.presentation.design_system.theme.TudeeTheme
import com.london.tudee.presentation.screens.category.delete_category.DeleteCategoryScreen
import com.london.tudee.presentation.screens.category.edit_category.EditCategoryScreen
import kotlinx.coroutines.delay
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CategoryDetailsScreen(
    categoryId: Int,
    onBackClick: () -> Unit,
    viewModel: CategoryDetailsViewModel = koinViewModel(),
) {

    LaunchedEffect(categoryId) {
        viewModel.initializeWithCategoryId(categoryId)
    }

    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(state.categoryDeleted) {
        if (state.categoryDeleted) {
            delay(1000)
            onBackClick()
        }
    }
    when {
        state.isLoading -> LoadingScreen(modifier = Modifier.fillMaxSize())
        state.errMessage != null -> ErrorScreen(modifier = Modifier.fillMaxSize())
        else -> CategoryDetailsContent(
            state = state,
            onBackClick = onBackClick,
            interactions = viewModel
        )
    }
}

@Composable
private fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(modifier) {
        Text(
            text = "Loading...",
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
private fun ErrorScreen(modifier: Modifier = Modifier) {
    Box(modifier) {
        Text(
            text = "There was an unexpected error",
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
private fun CategoryDetailsContent(
    state: CategoryDetailsUiState,
    onBackClick: () -> Unit,
    interactions: CategoryDetailsInteractions
) {
    Box {
        Column(
            modifier = Modifier
                .background(TudeeTheme.colors.surface)
                .padding(WindowInsets.statusBars.asPaddingValues())
        ) {

            TopAPPBar(
                onBackClick = onBackClick,
                state = state,
                onEditClick = interactions::showEditBottomSheet
            )

            TasksPagerSection(state = state)
        }
        if (state.isEditBottomSheetVisible) {
            EditCategoryScreen(
                category = state.category,
                onDismiss = interactions::hideEditBottomSheet,
                onDeleteClick = {
                    interactions.hideEditBottomSheet()
                    interactions.showDeleteBottomSheet()
                },
                onEditSuccess = {
                    interactions.hideEditBottomSheet()
                    interactions.onCategoryEdited()
                },
                onEditError = {
                    interactions.hideEditBottomSheet()
                    interactions.onCategoryEditError()
                }
            )
        }
        if (state.isDeleteBottomSheetVisible) {
            DeleteCategoryScreen(
                category = state.category,
                onDismiss = interactions::hideDeleteBottomSheet,
                onCategoryDeleted = {
                    interactions.hideDeleteBottomSheet()
                    interactions.onCategoryDeleted()
                },
                onDeleteError = {
                    interactions.hideDeleteBottomSheet()
                    interactions.onCategoryDeleteError()
                }
            )
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            when {
                state.showSuccessMessage -> {
                    state.stateMessage?.let { messageRes ->
                        SnackBar(
                            modifier = Modifier.offset(y = 56.dp),
                            message = messageRes,
                            iconPainter = painterResource(id = R.drawable.snack_bar_container),
                            iconTint = TudeeTheme.colors.greenAccent
                        )
                    }
                }

                state.showErrorMessage -> {
                    state.stateMessage?.let { messageRes ->
                        SnackBar(
                            modifier = Modifier.offset(y = 56.dp),
                            message = messageRes,
                            iconPainter = painterResource(id = R.drawable.snack_bar_error),
                            iconTint = TudeeTheme.colors.errorVariant,
                        )
                    }
                }
            }
            LaunchedEffect(state.stateMessage) {
                if (state.stateMessage != null) {
                    delay(3000)
                    interactions.clearMessages()
                }
            }
        }
    }
}

@Composable
private fun TasksPagerSection(state: CategoryDetailsUiState) {
    TudeeTabLayoutWithPager(
        tabs = listOf(
            TabItem(text = R.string.In_Progress, number = state.inProgressTasks.size),
            TabItem(text = R.string.To_Do, number = state.toDoTasks.size),
            TabItem(text = R.string.Done, number = state.doneTasks.size),
        ),
        tasksList = listOf(state.inProgressTasks, state.toDoTasks, state.doneTasks)
    )
    { _, tasks ->

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
private fun TopAPPBar(
    onBackClick: () -> Unit,
    state: CategoryDetailsUiState,
    onEditClick: () -> Unit = {}
) {
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
            if (!state.category.isDefault) {
                IconButton(
                    onClick = onEditClick,
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