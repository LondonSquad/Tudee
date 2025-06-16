package com.london.tudee.presentation.components.bottom_navigation_bar

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.london.tudee.R

@Composable
fun getDefaultBottomNavItems(): List<TudeeBottomNavItem> {
    return listOf(
        TudeeBottomNavItem(
            route =Routes.HOME,
            contentDescription = "Home",
            selectedIcon = painterResource(R.drawable.home_nav_bar_selected),
            unselectedIcon = painterResource(R.drawable.home_nav_bar_not_selected)
        ),
        TudeeBottomNavItem(
            route = Routes.TASKS,
            contentDescription = "Tasks",
            selectedIcon = painterResource(R.drawable.list_nav_bar_selected),
            unselectedIcon = painterResource(R.drawable.list_nav_bar_not_selected)
        ),
        TudeeBottomNavItem(
            route =Routes.CATEGORIES,
            contentDescription = "Dashboard",
            selectedIcon = painterResource(R.drawable.categories_nav_bar_selected),
            unselectedIcon = painterResource(R.drawable.categories_nav_bar_not_selected)
        )
    )
}

