package com.london.tudee.presentation.components.navigation_bar_bottom

import com.london.tudee.R
import com.london.tudee.presentation.navigation.Screen

object TudeeBottomNavItems {
    val items = listOf(
        TudeeBottomNavItem(
            screen = Screen.Home,
            contentDescription = null,
            selectedIcon = R.drawable.home_nav_bar_selected,
            unselectedIcon = R.drawable.home_nav_bar_not_selected
        ),
        TudeeBottomNavItem(
            screen = Screen.Tasks(),
            contentDescription = null,
            selectedIcon = R.drawable.list_nav_bar_selected,
            unselectedIcon = R.drawable.list_nav_bar_not_selected
        ),
        TudeeBottomNavItem(
            screen = Screen.Categories,
            contentDescription = null,
            selectedIcon = R.drawable.categories_nav_bar_selected,
            unselectedIcon = R.drawable.categories_nav_bar_not_selected
        )
    )
}