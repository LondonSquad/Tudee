package com.london.tudee.presentation.components.bottom_navigation_bar

import com.london.tudee.R

object TudeeBottomNavItems {
    val items = listOf(
        TudeeBottomNavItem(
            route = Routes.HOME,
            contentDescription = "Home",
            selectedIcon = R.drawable.home_nav_bar_selected,
            unselectedIcon = R.drawable.home_nav_bar_not_selected
        ),
        TudeeBottomNavItem(
            route = Routes.TASKS,
            contentDescription = "Tasks",
            selectedIcon = R.drawable.list_nav_bar_selected,
            unselectedIcon = R.drawable.list_nav_bar_not_selected
        ),
        TudeeBottomNavItem(
            route = Routes.CATEGORIES,
            contentDescription = "Dashboard",
            selectedIcon = R.drawable.categories_nav_bar_selected,
            unselectedIcon = R.drawable.categories_nav_bar_not_selected
        )
    )
}