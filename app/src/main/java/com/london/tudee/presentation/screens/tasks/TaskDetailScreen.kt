package com.london.tudee.presentation.screens.tasks

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.london.tudee.R
import com.london.tudee.presentation.components.buttons.TudeeSecondaryButton
import com.london.tudee.presentation.components.priority.Priority
import com.london.tudee.presentation.components.priority.PriorityBadge
import com.london.tudee.presentation.design_system.theme.ThemePreviews
import com.london.tudee.presentation.design_system.theme.TudeeTheme

@Composable
fun TaskDetailContent(
    taskName: String,
    taskDescription: String,
    taskStatus: Status,
    taskPriority: Priority,
    @DrawableRes icon: Int,
    modifier: Modifier = Modifier,
    onEditClick: () -> Unit = {},
    onMoveClick: () -> Unit = {}
) {

    Column(modifier = modifier) {
        Text(
            text = "Task Details",
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
            Status.TO_DO -> ActionsRow(
                onClickEdit = onEditClick,
                onClickMove = onMoveClick,
                taskStatusTitle = stringResource(R.string.move_to_in_progress)
            )

            Status.IN_PROGRESS -> ActionsRow(
                onClickEdit = onEditClick,
                onClickMove = onMoveClick,
                taskStatusTitle = stringResource(R.string.move_to_done)
            )

            Status.DONE -> {}
        }


    }

}


@Composable
private fun StatusRow(
    modifier: Modifier = Modifier, status: Status, priority: Priority
) {
    val titleColor = when (status) {
        Status.TO_DO -> TudeeTheme.colors.yellowAccent
        Status.IN_PROGRESS -> TudeeTheme.colors.purpleAccent
        Status.DONE -> TudeeTheme.colors.greenAccent
    }
    Row(modifier = modifier) {
        Box(
            modifier = Modifier
                .height(28.dp)
                .clip(TudeeTheme.shapes.circle)
                .background(
                    color = when (status) {
                        Status.TO_DO -> TudeeTheme.colors.yellowVariant
                        Status.IN_PROGRESS -> TudeeTheme.colors.purpleVariant
                        Status.DONE -> TudeeTheme.colors.greenVariant
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
                        Status.TO_DO -> stringResource(R.string.To_Do)
                        Status.IN_PROGRESS -> stringResource(R.string.In_Progress)
                        Status.DONE -> stringResource(R.string.Done)
                    },
                    style = TudeeTheme.typography.labelSmall,
                    color = titleColor,
                )
            }
        }
        Spacer(modifier = Modifier.width(8.dp))
        when (priority) {
            Priority.HIGH -> PriorityBadge(priority = Priority.HIGH, selected = true)
            Priority.MEDIUM -> PriorityBadge(priority = Priority.MEDIUM, selected = true)
            Priority.LOW -> PriorityBadge(priority = Priority.LOW, selected = true)
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
fun PreviewTaskDetail() {
    TaskDetailContent(
        taskStatus = Status.TO_DO,
        taskPriority = Priority.HIGH,
        taskName = "Task Name",
        taskDescription = "Task Description",
        icon = R.drawable.ic_education
    )
}


enum class Status {
    TO_DO, IN_PROGRESS, DONE
}