package com.london.tudee.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.london.tudee.presentation.design_system.theme.ThemePreviews
import com.london.tudee.presentation.design_system.theme.TudeeTheme

@Composable
fun DateItem(
    modifier: Modifier = Modifier,
    day: String,
    dayOfWeek: String,
    isSelected: Boolean = false,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .width(56.dp)
            .clickable(onClick = onClick)
            .background(
                brush = if (isSelected) {
                    Brush.linearGradient(
                        colors = TudeeTheme.colors.primaryGradient
                    )
                } else {
                    Brush.verticalGradient(
                        colors = listOf(TudeeTheme.colors.surface, TudeeTheme.colors.surface)
                    )
                },
                shape = TudeeTheme.shapes.small
            )
            .padding(
                horizontal = 14.dp, vertical = 12.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(
            text = day,
            style = TudeeTheme.typography.titleMedium,
            color = if (isSelected) TudeeTheme.colors.onPrimary else TudeeTheme.colors.body
        )

        Text(
            text = dayOfWeek,
            style = TudeeTheme.typography.labelMedium,
            color = if (isSelected) TudeeTheme.colors.caption else TudeeTheme.colors.hint
        )
    }
}

@ThemePreviews
@Composable
fun DateItemPreview() {
    TudeeTheme {
        Row(
            modifier = Modifier
                .background(TudeeTheme.colors.background)
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            DateItem(
                day = "15",
                dayOfWeek = "Mon",
                isSelected = true,
                onClick = {}
            )

            DateItem(
                day = "18",
                dayOfWeek = "Thu",
                isSelected = false,
                onClick = {}
            )
        }
    }
}