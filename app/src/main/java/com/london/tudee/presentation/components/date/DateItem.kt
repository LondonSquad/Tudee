package com.london.tudee.presentation.components.date

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.london.tudee.presentation.design_system.theme.ThemePreviews
import com.london.tudee.presentation.design_system.theme.TudeeTheme

@Composable
fun DateItem(
    dayOfMonth: String,
    dayOfWeek: String,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    onClick: () -> Unit
) {
    val backgroundColorStart by animateColorAsState(
        if (isSelected) TudeeTheme.colors.primaryGradient.first() else TudeeTheme.colors.surface,
    )
    val backgroundColorEnd by animateColorAsState(
        if (isSelected) TudeeTheme.colors.primaryGradient.last() else TudeeTheme.colors.surface,
    )
    val textColor by animateColorAsState(
        if (isSelected) TudeeTheme.colors.onPrimary else TudeeTheme.colors.body,
    )
    val captionColor by animateColorAsState(
        if (isSelected) TudeeTheme.colors.caption else TudeeTheme.colors.hint,
    )

    Column(
        modifier = modifier
            .width(56.dp)
            .clip(TudeeTheme.shapes.small)
            .clickable(onClick = onClick)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(backgroundColorStart, backgroundColorEnd)
                )
            )
            .padding(
                horizontal = 14.dp, vertical = 12.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(
            text = dayOfMonth,
            style = TudeeTheme.typography.titleMedium,
            color = textColor
        )

        Text(
            text = dayOfWeek,
            style = TudeeTheme.typography.labelMedium,
            color = captionColor
        )
    }
}

@ThemePreviews
@Composable
private fun DateItemPreview() {
    TudeeTheme {
        Row(
            modifier = Modifier
                .background(TudeeTheme.colors.background)
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            DateItem(
                dayOfMonth = "15",
                dayOfWeek = "Mon",
                isSelected = true,
                onClick = {}
            )

            DateItem(
                dayOfMonth = "18",
                dayOfWeek = "Thu",
                isSelected = false,
                onClick = {}
            )
        }
    }
}