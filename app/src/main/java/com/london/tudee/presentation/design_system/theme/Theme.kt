package com.example.tudeeapp.presentation.design_system.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import com.example.tudeeapp.presentation.design_system.color.LightTudeeColors
import com.example.tudeeapp.presentation.design_system.color.TudeeColors
import com.example.tudeeapp.presentation.design_system.shape.TudeeShapes
import com.example.tudeeapp.presentation.design_system.shape.TudeeThemeShape
import com.example.tudeeapp.presentation.design_system.text_style.TudeeTypography
import com.example.tudeeapp.presentation.design_system.text_style.TudeeTextStyle

object TudeeTheme {
    val colors: TudeeColors
        @Composable @ReadOnlyComposable get() = LocalTudeeColors.current

    val typography: TudeeTypography
        @Composable @ReadOnlyComposable get() = LocalTudeeTypography.current

    val shapes: TudeeShapes
        @Composable @ReadOnlyComposable get() = LocalTudeeShape.current
}

val LocalTudeeColors = staticCompositionLocalOf { LightTudeeColors }

val LocalTudeeTypography = staticCompositionLocalOf { TudeeTextStyle }

val LocalTudeeShape = staticCompositionLocalOf { TudeeThemeShape }

