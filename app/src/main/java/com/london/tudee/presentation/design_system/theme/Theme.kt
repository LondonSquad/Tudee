package com.london.tudee.presentation.design_system.theme

import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import com.london.tudee.presentation.design_system.color.LightTudeeColors
import com.london.tudee.presentation.design_system.color.TudeeColors
import com.london.tudee.presentation.design_system.shape.TudeeShapes
import com.london.tudee.presentation.design_system.shape.TudeeThemeShape
import com.london.tudee.presentation.design_system.text_style.TudeeTypography
import com.london.tudee.presentation.design_system.text_style.TudeeTextStyle

object TudeeTheme {
    val colors: TudeeColors
        @Composable @ReadOnlyComposable get() = LocalTudeeColors.current

    val typography: TudeeTypography
        @Composable @ReadOnlyComposable get() = LocalTudeeTypography.current

    val shapes: TudeeShapes
        @Composable @ReadOnlyComposable get() = LocalTudeeShape.current
}

object NoRippleTheme : RippleTheme {
    @Composable override fun defaultColor() = Color.Transparent
    @Composable override fun rippleAlpha() = RippleAlpha(0f, 0f, 0f, 0f)
}

val LocalTudeeColors = staticCompositionLocalOf { LightTudeeColors }

val LocalTudeeTypography = staticCompositionLocalOf { TudeeTextStyle }

val LocalTudeeShape = staticCompositionLocalOf { TudeeThemeShape }



