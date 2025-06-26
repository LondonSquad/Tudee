package com.london.tudee.presentation.components.buttons

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.london.tudee.R
import com.london.tudee.presentation.design_system.theme.ThemePreviews
import com.london.tudee.presentation.design_system.theme.TudeeTheme

@Composable
fun TudeeFloatingActionButton(
    painter: Painter,
    isEnabled: Boolean,
    contentDescription: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .size(64.dp)
            .clip(TudeeTheme.shapes.circle)
            .background(
                brush = Brush.verticalGradient(
                    colors = if (isEnabled) TudeeTheme.colors.primaryGradient else listOf(
                        TudeeTheme.colors.disabled,
                        TudeeTheme.colors.disabled
                    )
                )
            )
            .clickable(
                enabled = isEnabled,
            ) {
                onClick()
            }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier,
            painter = painter,
            contentDescription = contentDescription,
            colorFilter = if (isEnabled) {
                ColorFilter.tint(TudeeTheme.colors.onPrimary)
            } else {
                ColorFilter.tint(TudeeTheme.colors.stroke)
            }
        )
    }
}

@ThemePreviews
@Composable
private fun TudeeFloatingActionButtonPreview() {
    TudeeTheme {
        TudeeFloatingActionButton(
            painter = painterResource(R.drawable.note_add),
            isEnabled = true,
            contentDescription = "Add Note"
        )
    }
}