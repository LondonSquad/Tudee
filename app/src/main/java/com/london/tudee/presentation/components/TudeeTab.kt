package com.london.tudee.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.london.tudee.R
import com.london.tudee.presentation.design_system.theme.ThemePreviews
import com.london.tudee.presentation.design_system.theme.TudeeTheme

@Composable
fun TudeeTab(
    @StringRes text: Int,
    number: Int,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (!isSelected) {
        TabRow(modifier, onClick, text)
    } else {
        SelectedTabColumn(modifier, onClick, text, number)
    }
}

@Composable
private fun TabRow(modifier: Modifier, onClick: () -> Unit, @StringRes text: Int) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(TudeeTheme.colors.surfaceHigh)
            .padding(horizontal = 32.dp, vertical = 12.dp)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(text),
            style = TudeeTheme.typography.labelSmall,
            color = TudeeTheme.colors.hint,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun SelectedTabColumn(
    modifier: Modifier,
    onClick: () -> Unit,
    @StringRes text: Int,
    number: Int
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(TudeeTheme.colors.surfaceHigh)
            .padding(top = 2.dp)
            .clickable(onClick = onClick),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally
    ) {
        Layout(
            content = {
                TabContentRow(text, number)
                Spacer(modifier = Modifier.height(6.dp))
                TabIndicator()
            }
        ) { measurables, constraints ->
            val rowPlaceable = measurables[0].measure(constraints.copy(minWidth = 0))
            val spacerPlaceable = measurables[1].measure(constraints)
            val boxPlaceable = measurables[2].measure(constraints.copy(minWidth = rowPlaceable.width))

            val totalHeight = rowPlaceable.height + spacerPlaceable.height + boxPlaceable.height

            layout(constraints.maxWidth, totalHeight) {
                rowPlaceable.placeRelative((constraints.maxWidth - rowPlaceable.width) / 2, 0)
                spacerPlaceable.placeRelative(0, rowPlaceable.height)
                boxPlaceable.placeRelative((constraints.maxWidth - boxPlaceable.width) / 2, rowPlaceable.height + spacerPlaceable.height)
            }
        }
    }
}

@Composable
private fun TabContentRow(@StringRes text: Int, number: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(text),
            style = TudeeTheme.typography.labelMedium,
            color = TudeeTheme.colors.title
        )
        Spacer(modifier = Modifier.width(4.dp))
        Box(
            modifier = Modifier
                .size(28.dp)
                .clip(TudeeTheme.shapes.circle)
                .background(TudeeTheme.colors.surface),
            contentAlignment = Center
        ) {
            Text(
                text = number.toString(),
                style = TudeeTheme.typography.labelMedium,
                color = TudeeTheme.colors.body
            )
        }
    }
}

@Composable
private fun TabIndicator() {
    Box(
        modifier = Modifier
            .height(4.dp)
            .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
            .background(TudeeTheme.colors.secondary)
    )
}

@ThemePreviews
@Composable
private fun TudeeTabPreview() {
    TudeeTheme {
        TudeeTab(
            text = R.string.In_Progress,
            number = 14,
            isSelected = true,
            onClick = {},
            modifier = Modifier.fillMaxWidth()
        )
    }
}