package com.london.tudee.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
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
import com.london.tudee.presentation.components.priority.PriorityBadge
import com.london.tudee.presentation.design_system.theme.ThemePreviews
import com.london.tudee.presentation.design_system.theme.TudeeTheme

@Composable
fun TaskItem(
    modifier: Modifier = Modifier,
    priority: Priority,
    iconResId: Int,
    title: String,
    description: String,
    date: String? = null
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(123.dp),
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
            TaskItemIconSection(iconResId)
            Spacer(modifier = Modifier.width(8.dp))
            TaskItemTopBar(priority = priority, date = date)
        }

        TaskItemContent(
            modifier = Modifier,
            title = title,
            description = description
        )
    }
}

@Composable
private fun TaskItemIconSection(iconResId: Int) {
    Box(
        modifier = Modifier.size(56.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = iconResId),
            contentDescription = "Category Icon",
            tint = TudeeTheme.colors.pinkAccent
        )
    }
}

@Composable
private fun TaskItemTopBar(priority: Priority, date: String?) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        if (!date.isNullOrEmpty()) {
            DateBadge(dateText = date)
        }

        PriorityBadge(priority = priority)
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
            .padding(start = 4.dp, end = 12.dp, bottom = 24.dp)
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
            priority = Priority.HIGH,
            iconResId = R.drawable.ic_education,
            title = "Organize Study Desk",
            description = "Review cell structure and functions for tomorrow...",
            date = "12-04-2025"
        )
    }
}

@ThemePreviews
@Composable
fun PreviewTaskItemMedium() {
    TudeeTheme {
        TaskItem(
            priority = Priority.MEDIUM,
            iconResId = R.drawable.ic_education,
            title = "Organize Study Desk",
            description = "Review cell structure and functions for tomorrow..."
        )
    }
}

@ThemePreviews
@Composable
fun PreviewTaskItemLow() {
    TudeeTheme {
        TaskItem(
            priority = Priority.LOW,
            iconResId = R.drawable.ic_education,
            title = "Organize Study Desk",
            description = "Review cell structure and functions for tomorrow..."
        )
    }
}
