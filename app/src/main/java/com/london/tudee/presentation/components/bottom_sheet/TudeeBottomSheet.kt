package com.london.tudee.presentation.components.bottom_sheet

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.london.tudee.R
import com.london.tudee.presentation.components.buttons.TudeeNegativeButton
import com.london.tudee.presentation.components.buttons.TudeeSecondaryButton
import com.london.tudee.presentation.design_system.theme.ThemePreviews
import com.london.tudee.presentation.design_system.theme.TudeeTheme
import kotlin.math.roundToInt

@Composable
fun TudeeBottomSheet(
    visible: Boolean,
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = {},
    actions: @Composable ColumnScope.() -> Unit = {}
) {
    val configuration = LocalConfiguration.current
    val maxHeight = (configuration.screenHeightDp * 0.75).dp

    var offsetY by remember { mutableFloatStateOf(0f) }
    val dismissThreshold = 150f

    LaunchedEffect(visible) {
        if (visible) {
            offsetY = 0f
        }
    }

    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(
            initialOffsetY = { it },
            animationSpec = tween(300)
        ),
        exit = slideOutVertically(
            targetOffsetY = { it },
            animationSpec = tween(300)
        )
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .heightIn(max = maxHeight)
                .offset { IntOffset(0, offsetY.roundToInt()) }
                .clip(
                    TudeeTheme.shapes.large.copy(
                        bottomEnd = CornerSize(0.dp),
                        bottomStart = CornerSize(0.dp)
                    )
                )
                .background(TudeeTheme.colors.surface),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HorizontalDivider(
                thickness = 4.dp,
                color = TudeeTheme.colors.body.copy(alpha = 0.4f),
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .width(32.dp)
                    .clip(TudeeTheme.shapes.circle)
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragEnd = {
                                if (offsetY > dismissThreshold) {
                                    onDismiss()
                                } else {
                                    offsetY = 0f
                                }
                            }
                        ) { _, dragAmount ->
                            val newOffset = offsetY + dragAmount.y
                            offsetY = if (newOffset > 0) newOffset else 0f
                        }
                    }
            )

            Column {
                Column(
                    modifier = Modifier
                        .weight(1f, fill = false)
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp)
                ) {
                    content()
                }

                Column(
                    modifier = Modifier
                        .zIndex(1f)
                        .shadow(elevation = 20.dp)
                        .background(TudeeTheme.colors.surfaceHigh)
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                        .fillMaxWidth()
                ) {
                    actions()
                }
            }
        }
    }
}

@ThemePreviews
@Preview
@Composable
private fun BottomSheetPreview() {
    TudeeTheme {
        TudeeBottomSheet(
            visible = true,
            content = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(R.drawable.tudee_complment),
                        contentDescription = null,
                        modifier = Modifier.size(120.dp)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "Complete Task",
                        style = TudeeTheme.typography.headlineMedium,
                        color = TudeeTheme.colors.title
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Great job! You've successfully completed this task. Your progress has been saved and you're one step closer to achieving your goals.",
                        style = TudeeTheme.typography.bodyMedium,
                        color = TudeeTheme.colors.body,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )

                    Spacer(modifier = Modifier.height(24.dp))
                }
            },
            actions = {
                TudeeSecondaryButton(
                    text = "Continue",
                    onClick = {},
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(12.dp))
                TudeeNegativeButton(
                    text = "Mark as Incomplete",
                    onClick = {},
                    modifier = Modifier.fillMaxWidth()
                )
            }
        )
    }
}

