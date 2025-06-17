package com.london.tudee.presentation.design_system.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.london.tudee.presentation.design_system.color.DarkTudeeColors
import com.london.tudee.presentation.design_system.color.LightTudeeColors
import com.london.tudee.presentation.design_system.shape.TudeeThemeShape
import com.london.tudee.presentation.design_system.text_style.TudeeTextStyle

@Composable
fun TudeeTheme(
    isDarkMode: Boolean = isSystemInDarkTheme(),
    rippleTheme: RippleTheme = NoRippleTheme,
    content: @Composable () -> Unit
) {
    val colors = if (isDarkMode) DarkTudeeColors else LightTudeeColors

    CompositionLocalProvider(
        LocalTudeeColors provides colors,
        LocalTudeeTypography provides TudeeTextStyle,
        LocalTudeeShape provides TudeeThemeShape,
        LocalRippleTheme provides rippleTheme,
    ) {
        content()
    }
}

