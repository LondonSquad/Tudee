package com.london.tudee.presentation.components.bottom_navigation_bar

import androidx.annotation.DrawableRes
import com.london.tudee.presentation.navigation.Screen

data class TudeeBottomNavItem(
    val screen: Screen,
    val contentDescription: String?,
    @DrawableRes val selectedIcon: Int,
    @DrawableRes val unselectedIcon: Int
){
    val route: String get() = screen::class.qualifiedName ?: screen::class.java.name
}