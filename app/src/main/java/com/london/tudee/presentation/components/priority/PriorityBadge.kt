package com.london.tudee.presentation.components.priority

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.london.tudee.R
import com.london.tudee.presentation.design_system.theme.ThemePreviews
import com.london.tudee.presentation.design_system.theme.TudeeTheme

@Composable
fun PriorityBadge(
    modifier: Modifier = Modifier,
    priority: Priority,
    isSelected: Boolean = false,
    onClick: ((Priority) -> Unit)? = null
) {
    val resources = getPriorityResources(priority)

    val targetBackgroundColor = if (isSelected) resources.backgroundColor else TudeeTheme.colors.surfaceLow
    val targetContentColor = if (isSelected) TudeeTheme.colors.onPrimary else TudeeTheme.colors.hint

    val backgroundColor by animateColorAsState(
        targetValue = targetBackgroundColor,
        animationSpec = tween(durationMillis = 100)
    )
    val contentColor by animateColorAsState(
        targetValue = targetContentColor,
        animationSpec = tween(durationMillis = 100)
    )

    Card(
        modifier = modifier
            .height(28.dp)
            .wrapContentWidth()
            .clickable(
                enabled = onClick != null,
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                onClick?.invoke(priority)
            },
        shape = TudeeTheme.shapes.circle,
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Row(
            modifier = Modifier
                .wrapContentWidth()
                .fillMaxHeight()
                .padding(vertical = 6.dp)
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                modifier = Modifier
                    .padding(end = 2.dp)
                    .size(12.dp),
                painter = painterResource(id = resources.iconResId),
                contentDescription = "Priority Icon",
                tint = contentColor
            )
            Text(
                modifier = Modifier,
                text = stringResource(id = resources.textResId),
                style = TudeeTheme.typography.labelSmall,
                lineHeight = 16.sp,
                textAlign = TextAlign.Start,
                color = contentColor
            )
        }
    }
}

@Composable
fun getPriorityResources(priority: Priority): PriorityResources {
    return when (priority) {
        Priority.HIGH -> PriorityResources(
            iconResId = R.drawable.ic_priority_high,
            textResId = R.string.high,
            backgroundColor = TudeeTheme.colors.pinkAccent
        )

        Priority.MEDIUM -> PriorityResources(
            iconResId = R.drawable.ic_priority_medium,
            textResId = R.string.medium,
            backgroundColor = TudeeTheme.colors.yellowAccent
        )

        Priority.LOW -> PriorityResources(
            iconResId = R.drawable.ic_priority_low,
            textResId = R.string.low,
            backgroundColor = TudeeTheme.colors.greenAccent
        )
    }
}

@ThemePreviews
@Composable
fun PreviewPriorityBadgeHigh() {
    TudeeTheme {
        PriorityBadge(priority = Priority.HIGH, isSelected = true)
    }
}

@ThemePreviews
@Composable
fun PreviewPriorityBadgeMedium() {
    TudeeTheme {
        PriorityBadge(priority = Priority.MEDIUM, isSelected = true)
    }
}

@ThemePreviews
@Composable
fun PreviewPriorityBadgeLowSelected() {
    TudeeTheme {
        PriorityBadge(priority = Priority.LOW, isSelected = true)
    }
}

@ThemePreviews
@Composable
fun PreviewPriorityBadgeLow() {
    TudeeTheme {
        PriorityBadge(priority = Priority.LOW)
    }
}
