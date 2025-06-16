package com.london.tudee.presentation.components.buttons

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.london.tudee.R
import com.london.tudee.presentation.design_system.theme.ThemePreviews
import com.london.tudee.presentation.design_system.theme.TudeeTheme

@Composable
fun TudeeSecondaryButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String? = null,
    painter: Painter? = null,
    isEnabled: Boolean = true,
    isLoading: Boolean = false
) {
    Row(
        modifier = modifier
            .background(
                color = Color.Transparent,
                shape = TudeeTheme.shapes.circle
            )
            .border(
                width = 1.dp,
                color = TudeeTheme.colors.stroke,
                shape = TudeeTheme.shapes.circle
            )
            .clickable(
                enabled = isEnabled,
                onClick = onClick,
            )
            .height(56.dp)
            .padding(vertical = 8.dp, horizontal = 24.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        text?.let {
            Text(
                text = it,
                modifier = Modifier,
                style = TudeeTheme.typography.labelLarge,
                color = if (isEnabled) TudeeTheme.colors.primary else TudeeTheme.colors.disabled
            )
            if (isLoading) {
                LoadingLottieAnimation(
                    modifier = Modifier
                        .size(24.dp)
                        .padding(start = 8.dp),
                    tintColor = TudeeTheme.colors.onPrimary
                )
            }
        }

        painter?.let {
            Image(
                painter = it,
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp),
                colorFilter = ColorFilter.tint(TudeeTheme.colors.primary)
            )

        }
    }
}

@ThemePreviews
@Composable
private fun TuddeeSecondaryButtonPreview() {
    TudeeTheme {
        TudeeSecondaryButton(
            onClick = { },
            painter = painterResource(R.drawable.pencil_edit_01),
        )
    }
}

