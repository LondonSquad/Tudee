package com.london.tudee.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.toggleable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.london.tudee.R
import com.london.tudee.presentation.design_system.theme.ThemePreviews
import com.london.tudee.presentation.design_system.theme.TudeeTheme

@Composable
fun SwitchButton(
    isDarkMode: Boolean,
    onToggle: (Boolean) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val backgroundColor by animateColorAsState(
        targetValue = if (isDarkMode) TudeeTheme.colors.skyNightBackground else TudeeTheme.colors.primary,
        animationSpec = tween(1000)
    )
    Box(
        modifier = Modifier
            .height(36.dp)
            .width(64.dp)
            .clip(TudeeTheme.shapes.circle)
            .background(
                color = backgroundColor,
                shape = TudeeTheme.shapes.circle
            )
            .border(
                1.dp,
                TudeeTheme.colors.stroke,
                TudeeTheme.shapes.circle
            )
            .then(
                if (isDarkMode) Modifier.padding(end = 2.dp) else Modifier.padding(start = 1.dp)
            )
            .toggleable(
                value = isDarkMode,
                interactionSource = interactionSource,
                enabled = true,
                role = Role.Switch,
                indication = LocalIndication.current,
                onValueChange = { onToggle(it) }
            )
    ) {
        DownGrayCloud(isDarkMode)
        UpperGrayCloud(isDarkMode)
        MiddleWhiteCloud(isDarkMode)
        DownWhiteCloud(!isDarkMode)

        AnimatedVisibility(
            visible = !isDarkMode,
            enter = fadeIn(
                animationSpec = tween(
                    durationMillis = 1000,
                    easing = EaseOut
                )
            ),
            exit = slideOutHorizontally(
                targetOffsetX = { it },
                animationSpec = tween(
                    durationMillis = 1000,
                    easing = EaseOut
                )
            ) + fadeOut(
                animationSpec = tween(durationMillis = 1000, easing = EaseOut)
            ),
            modifier = Modifier.align(Alignment.CenterStart)
        ) {

            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(
                        brush = Brush.linearGradient(
                            listOf(
                                TudeeTheme.colors.yellowAccent,
                                TudeeTheme.colors.secondary,
                            )
                        ),
                        shape = TudeeTheme.shapes.circle
                    )
            )
        }

        AnimatedVisibility(
            visible = isDarkMode,
            enter = fadeIn(
                animationSpec = tween(
                    durationMillis = 1000,
                    easing = EaseOut
                )
            ),
            exit = slideOutHorizontally(
                targetOffsetX = { -it },
                animationSpec = tween(
                    durationMillis = 800,
                    easing = EaseOut
                )
            ) + fadeOut(
                animationSpec = tween(durationMillis = 1000, easing = EaseOut)
            ),
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(
                        brush = Brush.linearGradient(
                            TudeeTheme.colors.linearMoodColor
                        ),
                        shape = TudeeTheme.shapes.circle
                    )
            )
        }

        if (isDarkMode) {
            Image(
                painter = painterResource(R.drawable.ellipse_stars),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 5.94.dp)
            )
        }

        BigCircleInMoon(isDarkMode)

        SmallInMoon(isDarkMode)

        UpperWhiteCloud(isDarkMode)
    }
}

@Composable
private fun BoxScope.UpperWhiteCloud(isDarkMood: Boolean) {
    val offsetX by animateDpAsState(
        targetValue = if (isDarkMood) (-17).dp else 13.5.dp,
        animationSpec = tween(durationMillis = 1000, easing = EaseOut)
    )

    val offsetY by animateDpAsState(
        targetValue = if (isDarkMood) 4.dp else 0.dp,
        animationSpec = tween(durationMillis = 1000, easing = EaseOut)
    )

    val size by animateDpAsState(
        targetValue = if (isDarkMood) 8.dp else (29).dp,
        animationSpec = tween(durationMillis = 1000, easing = EaseOut)
    )

    val backgroundColor by animateColorAsState(
        targetValue = if (isDarkMood) {
            TudeeTheme.colors.upperSmallCircleMoon
        } else {
            TudeeTheme.colors.surfaceHigh
        },
        animationSpec = tween(durationMillis = 1000, easing = EaseOut)
    )

    Box(
        modifier = Modifier
            .size(size)
            .offset {
                IntOffset(
                    x = offsetX.roundToPx(),
                    y = offsetY.roundToPx()
                )
            }
            .align(Alignment.TopEnd)
            .background(
                color = backgroundColor,
                shape = TudeeTheme.shapes.circle
            )
            .then(
                if (isDarkMood) Modifier.innerShadow() else Modifier
            )
    )
}

private fun Modifier.innerShadow() = this.drawWithContent {
    drawContent()
    drawCircle(
        brush = Brush.radialGradient(
            colors = listOf(Color.Transparent, Color(0xffC9D9FF)),
            center = Offset(size.width / 1.5f, size.height / 1.5f),
            radius = size.minDimension / 2
        )
    )
}

@Composable
private fun BoxScope.MiddleWhiteCloud(isDarkMood: Boolean) {
    val density = LocalDensity.current
    val offsetX by animateDpAsState(
        targetValue = if (isDarkMood) (29).dp else (-1).dp,
        animationSpec = tween(durationMillis = 1000, easing = EaseOut)
    )

    val offsetY by animateDpAsState(
        targetValue = if (isDarkMood) 4.dp else 5.dp,
        animationSpec = tween(durationMillis = 1000, easing = EaseOut)
    )

    val size by animateDpAsState(
        targetValue = if (isDarkMood) 16.dp else (16).dp,
        animationSpec = tween(durationMillis = 1000, easing = EaseOut)
    )
    Box(
        modifier = Modifier
            .size(size)
            .align(Alignment.BottomEnd)
            .offset {
                IntOffset(
                    x = with(density) { offsetX.roundToPx() },
                    y = with(density) { offsetY.roundToPx() }
                )
            }
            .background(
                color = TudeeTheme.colors.surfaceHigh,
                shape = TudeeTheme.shapes.circle
            )
    )
}

@Composable
private fun BoxScope.UpperGrayCloud(isDarkMood: Boolean) {
    val density = LocalDensity.current
    val offsetX by animateDpAsState(
        targetValue = if (isDarkMood) 33.dp else 12.3.dp,
        animationSpec = tween(durationMillis = 1000, easing = EaseOut)
    )

    val offsetY by animateDpAsState(
        targetValue = if (isDarkMood) (-3).dp else (-3).dp,
        animationSpec = tween(durationMillis = 1000, easing = EaseOut)
    )

    val size by animateDpAsState(
        targetValue = if (isDarkMood) 32.dp else (32).dp,
        animationSpec = tween(durationMillis = 1000, easing = EaseOut)
    )

    Box(
        modifier = Modifier
            .size(size)
            .align(Alignment.TopEnd)
            .offset {
                IntOffset(
                    x = with(density) { offsetX.roundToPx() },
                    y = with(density) { offsetY.roundToPx() }
                )
            }
            .background(
                color = TudeeTheme.colors.surfaceLow,
                shape = TudeeTheme.shapes.circle
            )
    )
}

@Composable
private fun BoxScope.DownGrayCloud(isDarkMood: Boolean) {
    val offsetX by animateDpAsState(
        targetValue = if (isDarkMood) (-8).dp else (-8).dp,
        animationSpec = tween(durationMillis = 1000, easing = EaseOut)
    )

    val offsetY by animateDpAsState(
        targetValue = if (isDarkMood) 25.dp else 8.dp,
        animationSpec = tween(durationMillis = 1000, easing = EaseOut)
    )

    val size by animateDpAsState(
        targetValue = if (isDarkMood) 24.dp else (24).dp,
        animationSpec = tween(durationMillis = 1000, easing = EaseOut)
    )

    Box(
        modifier = Modifier
            .size(size)
            .align(Alignment.BottomEnd)
            .offset {
                IntOffset(
                    x = offsetX.roundToPx(),
                    y = offsetY.roundToPx()
                )
            }
            .background(
                color = TudeeTheme.colors.surfaceLow,
                shape = TudeeTheme.shapes.circle
            )
    )
}

@Composable
private fun BoxScope.DownWhiteCloud(isDarkMood: Boolean) {
    AnimatedVisibility(
        visible = isDarkMood,
        enter = slideIn(
            initialOffset = { IntOffset((-1.5 * it.width).toInt(), (it.height / 2)) },
            animationSpec = tween(
                durationMillis = 1000,
                easing = EaseOut
            )
        ) + scaleIn(
            initialScale = 0.5f,
            animationSpec = tween(
                durationMillis = 1000,
                easing = EaseOut
            )
        ) + fadeIn(
            animationSpec = tween(
                durationMillis = 1000,
                easing = EaseOut
            )
        ),
        exit = slideOut(
            targetOffset = { IntOffset((-1.5 * it.width).toInt(), (it.height / 2)) },
            animationSpec = tween(
                durationMillis = 1000,
                easing = EaseOut
            )
        ) + scaleOut(
            targetScale = 0.5f,
            animationSpec = tween(
                durationMillis = 1000,
                easing = EaseOut
            )
        ) + fadeOut(
            animationSpec = tween(
                durationMillis = 1000,
                easing = EaseOut
            )
        ),
        modifier = Modifier
            .align(Alignment.BottomEnd)
            .offset(x = (-14.5).dp, y = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .width(14.dp)
                .height(16.dp)
                .background(
                    color = TudeeTheme.colors.surfaceHigh,
                    shape = TudeeTheme.shapes.circle
                )
        )
    }
}

@Composable
private fun BoxScope.BigCircleInMoon(isDarkMood: Boolean) {
    AnimatedVisibility(
        visible = isDarkMood,
        enter = fadeIn(
            tween(durationMillis = 300, easing = EaseOut)
        ),
        exit = fadeOut(
            tween(durationMillis = 300, easing = EaseOut)
        ),
        modifier = Modifier.align(Alignment.BottomEnd)
    ) {
        Box(
            modifier = Modifier
                .size(14.dp)
                .offset(x = (-16).dp, y = (-8).dp)
                .innerShadow()
                .background(
                    Color(0x01FFFFFF),
                    TudeeTheme.shapes.circle
                )
        )
    }
}

@Composable
private fun BoxScope.SmallInMoon(isDarkMood: Boolean) {
    AnimatedVisibility(
        visible = isDarkMood,
        enter = fadeIn(
            tween(durationMillis = 300, easing = EaseOut)
        ),
        exit = fadeOut(
            tween(durationMillis = 300, easing = EaseOut)
        ),
        modifier = Modifier.align(Alignment.BottomEnd)
    ) {
        Box(
            modifier = Modifier
                .size(4.dp)
                .offset(x = (-11).dp, y = (-6).dp)
                .innerShadow()
                .background(
                    Color(0x01FFFFFF),
                    TudeeTheme.shapes.circle
                )
        )
    }
}

@ThemePreviews
@Composable
private fun PreviewSwitchButton() {
    var isDarkMood by remember { mutableStateOf(false) }
    TudeeTheme {
        SwitchButton(
            isDarkMode = isDarkMood,
            onToggle = { isDarkMood = !isDarkMood }
        )
    }
}