package com.london.tudee.presentation.components.tabs


import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
    val interactionSource = remember { MutableInteractionSource() }

    if (!isSelected) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .background(TudeeTheme.colors.surfaceHigh)
                .padding(horizontal = 32.dp, vertical = 12.dp)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = onClick
                ),
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
    } else {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(TudeeTheme.colors.surfaceHigh)
                .padding(top = 6.dp)
                .clickable(onClick = onClick),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = CenterHorizontally
        ) {
            Layout(
                content = {
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
                                .clip(shape = TudeeTheme.shapes.circle)
                                .background(color = TudeeTheme.colors.surface),
                            contentAlignment = Center
                        ) {
                            Text(
                                text = number.toString(),
                                style = TudeeTheme.typography.labelMedium,
                                color = TudeeTheme.colors.body
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Box(
                        modifier = Modifier
                            .height(4.dp)
                            .clip(shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                            .background(color = TudeeTheme.colors.secondary)
                    )
                }
            ) { measurables, constraints ->
                val rowMeasurable = measurables[0]
                val rowPlaceable = rowMeasurable.measure(
                    constraints.copy(minWidth = 0, maxWidth = constraints.maxWidth)
                )
                val spacerMeasurable = measurables[1]
                val spacerPlaceable = spacerMeasurable.measure(constraints)
                val boxMeasurable = measurables[2]
                val boxPlaceable = boxMeasurable.measure(
                    constraints.copy(minWidth = rowPlaceable.width, maxWidth = rowPlaceable.width)
                )
                val height = rowPlaceable.height + spacerPlaceable.height + boxPlaceable.height
                layout(constraints.maxWidth, height) {
                    rowPlaceable.placeRelative(
                        x = (constraints.maxWidth - rowPlaceable.width) / 2,
                        y = 0
                    )
                    spacerPlaceable.placeRelative(
                        x = 0,
                        y = rowPlaceable.height
                    )
                    boxPlaceable.placeRelative(
                        x = (constraints.maxWidth - boxPlaceable.width) / 2,
                        y = rowPlaceable.height + spacerPlaceable.height
                    )
                }
            }
        }
    }
}

@ThemePreviews
@Composable
private fun TudeeTabPreview() {
    TudeeTheme {
        TudeeTab(
            text = R.string.In_Progress,
            number = 14,
            isSelected = false,
            onClick = {},
            modifier = Modifier.fillMaxWidth()
        )
    }
}

