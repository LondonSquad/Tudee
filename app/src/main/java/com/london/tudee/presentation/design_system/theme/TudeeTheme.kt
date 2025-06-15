package com.example.tudeeapp.presentation.design_system.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.example.tudeeapp.presentation.design_system.color.DarkTudeeColors
import com.example.tudeeapp.presentation.design_system.color.LightTudeeColors
import com.example.tudeeapp.presentation.design_system.shape.TudeeThemeShape
import com.example.tudeeapp.presentation.design_system.text_style.TudeeTextStyle

@Composable
fun TudeeTheme(
    isDarkMode: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (isDarkMode) DarkTudeeColors else LightTudeeColors

    CompositionLocalProvider(
        LocalTudeeColors provides colors,
        LocalTudeeTypography provides TudeeTextStyle,
        LocalTudeeShape provides TudeeThemeShape
    ) {
        content()
    }
}

