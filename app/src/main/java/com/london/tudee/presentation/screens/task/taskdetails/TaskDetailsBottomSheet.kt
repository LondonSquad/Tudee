package com.london.tudee.presentation.screens.task.taskdetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.london.tudee.R
import com.london.tudee.domain.entities.Priority
import com.london.tudee.domain.entities.TaskStatus
import com.london.tudee.presentation.components.bottom_sheet.TudeeBottomSheetScreen
import com.london.tudee.presentation.components.buttons.TudeeSecondaryButton
import com.london.tudee.presentation.components.priority.PriorityBadge
import com.london.tudee.presentation.design_system.theme.ThemePreviews
import com.london.tudee.presentation.design_system.theme.TudeeTheme
import com.london.tudee.presentation.utils.converterStringToBitmap
import org.koin.androidx.compose.koinViewModel

@Composable
fun TaskDetailsBottomSheet(
    taskId: Int,
    viewModel: TaskDetailsBottomSheetViewModel = koinViewModel(),
    onDismiss: () -> Unit = {}
) {

    val taskDetailsUiState by viewModel.uiState.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.loadTask(taskId)
    }
    TaskDetailsBottomSheetContent(
        taskName = taskDetailsUiState.task.title,
        taskDescription = taskDetailsUiState.task.description,
        taskStatus = taskDetailsUiState.task.taskStatus,
        taskPriority = taskDetailsUiState.task.priority,
        icon = taskDetailsUiState.categoryIcon,
        onEditClick = viewModel::onEditClick,
        onMoveClick = viewModel::onClickMove,
        onDismiss = onDismiss,
    )
    if (taskDetailsUiState.isEditBottomSheetVisible) {
        TODO()// edit bottom sheet should be here send task id to it
    }
}

@Composable
private fun TaskDetailsBottomSheetContent(
    taskName: String,
    taskDescription: String,
    taskStatus: TaskStatus,
    taskPriority: Priority,
    icon: String,
    onEditClick: () -> Unit = {},
    onMoveClick: () -> Unit = {},
    onDismiss: () -> Unit = {},
    showBottomSheet: Boolean = true
) {
    TudeeBottomSheetScreen(
        showBottomSheet = showBottomSheet,
        onDismiss = onDismiss,
        screenContent = {},
        bottomSheetActions = {},
        showActions = false,
        bottomSheetContent = {
            TaskDetailsBottomSheetContent(
                taskName = taskName,
                taskDescription = taskDescription,
                taskStatus = taskStatus,
                taskPriority = taskPriority,
                icon = icon,
                onEditClick = onEditClick,
                onMoveClick = onMoveClick
            )
        })
}


@Composable
private fun TaskDetailsBottomSheetContent(
    taskName: String,
    taskDescription: String,
    taskStatus: TaskStatus,
    taskPriority: Priority,
    icon: String,
    onEditClick: () -> Unit = {},
    onMoveClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier.padding(bottom = 12.dp)
    ) {
        Text(
            text = stringResource(R.string.task_details),
            style = TudeeTheme.typography.titleLarge,
            color = TudeeTheme.colors.title,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        Box(
            modifier = Modifier
                .size(56.dp)
                .background(
                    color = TudeeTheme.colors.surfaceHigh, shape = TudeeTheme.shapes.circle
                ), contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = rememberAsyncImagePainter(converterStringToBitmap(icon)),
                contentDescription = "Category Icon",
                tint = TudeeTheme.colors.pinkAccent,
                modifier = Modifier.size(32.dp)
            )
        }
        Text(
            text = taskName,
            style = TudeeTheme.typography.titleMedium,
            color = TudeeTheme.colors.title,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Text(
            text = taskDescription,
            style = TudeeTheme.typography.bodySmall,
            color = TudeeTheme.colors.body,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        Box(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
                .background(color = TudeeTheme.colors.stroke)
        )
        StatusRow(
            status = taskStatus,
            priority = taskPriority,
            modifier = Modifier.padding(top = 12.dp, bottom = 24.dp)
        )
        when (taskStatus) {
            TaskStatus.TODO -> ActionsRow(
                onClickEdit = onEditClick,
                onClickMove = onMoveClick,
                taskStatusTitle = stringResource(R.string.move_to_in_progress)
            )

            TaskStatus.IN_PROGRESS -> ActionsRow(
                onClickEdit = onEditClick,
                onClickMove = onMoveClick,
                taskStatusTitle = stringResource(R.string.move_to_done)
            )

            TaskStatus.DONE -> {}
        }


    }
}


@Composable
private fun StatusRow(
    modifier: Modifier = Modifier, status: TaskStatus, priority: Priority
) {
    val titleColor = when (status) {
        TaskStatus.TODO -> TudeeTheme.colors.yellowAccent
        TaskStatus.IN_PROGRESS -> TudeeTheme.colors.purpleAccent
        TaskStatus.DONE -> TudeeTheme.colors.greenAccent
    }
    Row(modifier = modifier) {
        Box(
            modifier = Modifier
                .height(28.dp)
                .clip(TudeeTheme.shapes.circle)
                .background(
                    color = when (status) {
                        TaskStatus.TODO -> TudeeTheme.colors.yellowVariant
                        TaskStatus.IN_PROGRESS -> TudeeTheme.colors.purpleVariant
                        TaskStatus.DONE -> TudeeTheme.colors.greenVariant
                    }
                ), contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 12.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.task_status_icon_dot),
                    contentDescription = "dot",
                    tint = titleColor,
                    modifier = Modifier
                        .padding(end = 4.dp)
                        .size(5.dp)
                )
                Text(
                    text = when (status) {
                        TaskStatus.TODO -> stringResource(R.string.To_Do)
                        TaskStatus.IN_PROGRESS -> stringResource(R.string.In_Progress)
                        TaskStatus.DONE -> stringResource(R.string.Done)
                    },
                    style = TudeeTheme.typography.labelSmall,
                    color = titleColor,
                )
            }
        }
        Spacer(modifier = Modifier.width(8.dp))
        when (priority) {
            Priority.HIGH -> PriorityBadge(priority = Priority.HIGH, isSelected = true)
            Priority.MEDIUM -> PriorityBadge(priority = Priority.MEDIUM, isSelected = true)
            Priority.LOW -> PriorityBadge(priority = Priority.LOW, isSelected = true)
        }
    }
}

@Composable
private fun ActionsRow(
    onClickEdit: () -> Unit,
    onClickMove: () -> Unit,
    modifier: Modifier = Modifier,
    taskStatusTitle: String,
) {
    Row(modifier = modifier) {
        TudeeSecondaryButton(
            onClick = onClickEdit,
            painter = painterResource(R.drawable.pencil_edit_01),
            modifier = Modifier.padding(end = 4.dp)
        )
        TudeeSecondaryButton(
            text = taskStatusTitle, onClick = onClickMove, modifier = Modifier.weight(1f)
        )
    }
}

@ThemePreviews
@Composable
private fun PreviewTaskDetail() {
    TaskDetailsBottomSheetContent(
        taskName = "Task Name",
        taskDescription = "Task Description",
        taskStatus = TaskStatus.TODO,
        taskPriority = Priority.HIGH,
        icon ="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 ",
        onEditClick = { },
        onMoveClick = {},
        onDismiss = {},
        showBottomSheet = true
    )
}


