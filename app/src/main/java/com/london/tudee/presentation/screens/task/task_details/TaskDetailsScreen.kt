package com.london.tudee.presentation.screens.task.task_details

import androidx.annotation.DrawableRes
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.london.tudee.R
import com.london.tudee.domain.entities.Priority
import com.london.tudee.domain.entities.TaskStatus
import com.london.tudee.presentation.components.bottom_sheet.TudeeBottomSheetScreen
import com.london.tudee.presentation.components.buttons.TudeeSecondaryButton
import com.london.tudee.presentation.components.priority.PriorityBadge
import com.london.tudee.presentation.design_system.theme.ThemePreviews
import com.london.tudee.presentation.design_system.theme.TudeeTheme
import org.koin.androidx.compose.koinViewModel
import androidx.compose.runtime.getValue

@Composable
fun TaskDetailsScreen(
    taskId: Int,
    viewModel: TaskDetailsViewModel = koinViewModel(),
    onEditClick: () -> Unit = {},
    onDismiss: () -> Unit = {},
    showBottomSheet: Boolean = false
) {

    val taskDetailsUiState by viewModel.uiState.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.loadTask(taskId)
    }
    TaskDetailsScreenContent(
        taskName = taskDetailsUiState.task.title,
        taskDescription = taskDetailsUiState.task.description,
        taskStatus = taskDetailsUiState.task.taskStatus,
        taskPriority = taskDetailsUiState.task.priority,
        icon = taskDetailsUiState.categoryIcon,
        onEditClick = onEditClick,
        onMoveClick = viewModel::onClickMove,
        onDismiss = onDismiss,
        showBottomSheet = showBottomSheet
    )

}

@Composable
private fun TaskDetailsScreenContent(
    taskName: String,
    taskDescription: String,
    taskStatus: TaskStatus,
    taskPriority: Priority,
    @DrawableRes icon: Int,
    onEditClick: () -> Unit = {},
    onMoveClick: () -> Unit = {},
    onDismiss: () -> Unit = {},
    showBottomSheet: Boolean = false
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
    @DrawableRes icon: Int,
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
                painter = painterResource(icon),
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
    TaskDetailsScreenContent(
        taskName = "Task Name",
        taskDescription = "Task Description",
        taskStatus = TaskStatus.TODO,
        taskPriority = Priority.HIGH,
        icon = R.drawable.ic_education,
        onEditClick = { },
        onMoveClick = {},
        onDismiss = {},
        showBottomSheet = true
    )
}


