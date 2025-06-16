package com.london.tudee.presentation.components.bottom_navigation_bar

import androidx.compose.ui.graphics.painter.Painter

data class TudeeBottomNavItem(
    val route: String,
    val contentDescription: String,
    val selectedIcon: Painter,
    val unselectedIcon: Painter
)