package com.london.tudee.presentation.components.buttons

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.london.tudee.presentation.design_system.theme.ThemePreviews
import com.london.tudee.presentation.design_system.theme.TudeeTheme

@Composable
fun TudeeNegativeTextButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    isDisabled: Boolean = false,
    isLoading: Boolean = false
) {
    if (isDisabled.not()) {
        Row(
            modifier = modifier.clickable(onClick = onClick),
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
                    tintColor = TudeeTheme.colors.error,
                )
            }
        }
    } else {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                text = text,
                style = TudeeTheme.typography.labelLarge,
                color = TudeeTheme.colors.disabled,
            )
        }
    }
}


@Composable
@ThemePreviews
private fun PreviewTudeeNegativeTextButton() {
    TudeeTheme {
        TudeeNegativeTextButton(
            onClick = {}, text = "Cancel", isLoading = true
        )
    }
}