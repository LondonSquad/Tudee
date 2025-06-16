package com.london.tudee.presentation.components.buttons

import androidx.compose.foundation.background
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.london.tudee.presentation.design_system.theme.ThemePreviews
import com.london.tudee.presentation.design_system.theme.TudeeTheme

@Composable
fun TudeeNegativeButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    isDisabled: Boolean = false,
    isLoading: Boolean = false
) {
    if (isDisabled.not()) {
        Row(
            modifier = modifier
                .height(56.dp)
                .clip(TudeeTheme.shapes.circle)
                .background(
                    TudeeTheme.colors.errorVariant
                )
                .clickable(onClick = onClick)
                .padding(horizontal = 24.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                text = text,
                style = TudeeTheme.typography.labelLarge,
                color = TudeeTheme.colors.error,
            )
            if (isLoading) {
                LoadingLottieAnimation(
                    modifier = Modifier
                        .size(24.dp)
                        .padding(start = 8.dp),
                    tintColor = TudeeTheme.colors.error
                )
            }
        }
    } else {
        Row(
            modifier = modifier
                .height(56.dp)
                .clip(TudeeTheme.shapes.circle)
                .background(
                    TudeeTheme.colors.disabled
                )
                .padding(horizontal = 24.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                text = text,
                style = TudeeTheme.typography.labelLarge,
                color = TudeeTheme.colors.stroke,
            )
        }
    }
}

@Composable
@ThemePreviews
private fun PreviewTudeeNegativeButton() {
    TudeeTheme {
        TudeeNegativeButton(
            onClick = {}, text = "Submit", isLoading = true
        )
    }
}