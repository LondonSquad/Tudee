package com.london.tudee.presentation.components.bottom_navigation_bar

import androidx.annotation.DrawableRes

data class TudeeBottomNavItem(
    val route: String,
    val contentDescription: String?,
    @DrawableRes val selectedIcon: Int,
    @DrawableRes val unselectedIcon: Int
)