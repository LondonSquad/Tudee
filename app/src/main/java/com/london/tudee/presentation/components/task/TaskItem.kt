package com.london.tudee.presentation.components.task

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.london.tudee.R
import com.london.tudee.domain.entities.Priority
import com.london.tudee.domain.entities.Task
import com.london.tudee.presentation.components.date.DateBadge
import com.london.tudee.presentation.components.priority.PriorityBadge
import com.london.tudee.presentation.design_system.theme.ThemePreviews
import com.london.tudee.presentation.design_system.theme.TudeeTheme
import com.london.tudee.domain.entities.TaskStatus
import com.london.tudee.presentation.utils.formatDate
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant


@Composable
fun TaskItem(
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    task: Task,
    hasDate: Boolean,
    onTaskClicked: (Task) -> Unit
) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(111.dp)
            .clickable{ onTaskClicked(task) },
        shape = TudeeTheme.shapes.small,
        colors = CardDefaults.cardColors(containerColor = TudeeTheme.colors.surfaceHigh)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp, end = 12.dp, top = 4.dp, bottom = 2.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TaskItemIconSection(task.categoryId)
            Spacer(modifier = Modifier.width(8.dp))
            TaskItemTopBar(
                priority = task.priority,
                date = formatDate(task.timeStamp),
                isSelected = isSelected,
                hasDate = hasDate
            )
        }

        TaskItemContent(
            title = task.title,
            description = task.description
        )
    }
}

@Composable
private fun TaskItemIconSection(@DrawableRes iconResId: Int) {
    Box(
        modifier = Modifier.size(56.dp), contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = iconResId),
            contentDescription = "Category Icon",
        )
    }
}

@Composable
private fun TaskItemTopBar(
    modifier: Modifier = Modifier,
    priority: Priority,
    date: String?,
    isSelected: Boolean,
    hasDate: Boolean
) {
    Row(
        modifier = modifier.height(28.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        if (!date.isNullOrEmpty()) {
            DateBadge(
                modifier = Modifier.height(28.dp),
                shape = TudeeTheme.shapes.circle,
                colors = CardDefaults.cardColors(containerColor = TudeeTheme.colors.surface),
                dateText = "12-03-2025",
                iconSize = 12.dp,
                textSize = 13.sp,
                textStyle = TudeeTheme.typography.labelSmall,
                lineHeight = 16.sp,
                iconColor = TudeeTheme.colors.body,
                textColor = TudeeTheme.colors.body,
                contentPadding = PaddingValues(vertical = 6.dp, horizontal = 8.dp),
                isVisible = hasDate
            )
        }

        PriorityBadge(priority = priority, isSelected = isSelected)
    }
}

@Composable
private fun TaskItemContent(
    modifier: Modifier = Modifier,
    title: String,
    description: String
) {
    Column(
        modifier = modifier
            .wrapContentHeight()
            .fillMaxWidth(0.9f)
            .padding(start = 4.dp, end = 12.dp, bottom = 12.dp)
    ) {
        Text(
            modifier = Modifier.padding(start = 8.dp, bottom = 2.dp),
            text = title.take(25),
            style = TudeeTheme.typography.labelLarge,
            lineHeight = 19.sp,
            maxLines = 1,
            textAlign = TextAlign.Start,
            color = TudeeTheme.colors.body
        )
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = description,
            style = TudeeTheme.typography.labelSmall,
            lineHeight = 16.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Start,
            color = TudeeTheme.colors.body
        )
    }
}

@ThemePreviews
@Composable
fun PreviewTaskItemHigh() {
    TudeeTheme {
        TaskItem(
            task = Task(
                id = 1,
                priority = Priority.HIGH,
                categoryId = R.drawable.ic_education,
                title = "Organize Study Desk",
                description = "Review cell structure and functions for tomorrow...",
                timeStamp = Instant.parse("2023-09-20T00:00:00Z"),
                taskStatus = TaskStatus.TODO
            ),
            hasDate = true,
            onTaskClicked = {}
        )
    }
}

@ThemePreviews
@Composable
fun PreviewTaskItemMedium() {
    TudeeTheme {
        TaskItem(
            task = Task(
                id = 1,
                priority = Priority.MEDIUM,
                categoryId = R.drawable.ic_education,
                title = "Organize Study Desk",
                description = "Review cell structure and functions for tomorrow...",
                timeStamp =  Instant.parse("2023-09-20T00:00:00Z"),
                taskStatus = TaskStatus.TODO
            ),
            hasDate = true,
            onTaskClicked = {}
        )
    }
}

@ThemePreviews
@Composable
fun PreviewTaskItemLow() {
    TudeeTheme {
        TaskItem(
            task = Task(
                id = 1,
                priority = Priority.LOW,
                categoryId = R.drawable.ic_education,
                title = "Organize Study Desk",
                description = "Review cell structure and functions for tomorrow...",
                timeStamp = Instant.parse("2023-09-20T00:00:00Z"),
                taskStatus = TaskStatus.TODO
            ),
            hasDate = false,
            onTaskClicked = {}
        )
    }
}