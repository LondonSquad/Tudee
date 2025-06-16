package com.london.tudee.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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
                        colors = listOf(
                            Color(0xFF49BAF2), // OverlayLight
                            Color(0xFF3A9CCD)
                        )
                    )
                } else {
                    Brush.verticalGradient(
                        colors = listOf(TudeeTheme.colors.surfaceLow, TudeeTheme.colors.surfaceLow)
                    )
                },
                shape = RoundedCornerShape(16.dp)
            )
            .padding(
                top = 12.dp,
                bottom = 12.dp,
                start = 14.dp,
                end = 14.dp
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
            style = TudeeTheme.typography.labelSmall,
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