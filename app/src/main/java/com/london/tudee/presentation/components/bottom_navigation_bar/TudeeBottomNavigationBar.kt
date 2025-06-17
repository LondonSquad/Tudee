package com.london.tudee.presentation.components.bottom_navigation_bar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.london.tudee.presentation.design_system.theme.ThemePreviews
import com.london.tudee.presentation.design_system.theme.TudeeTheme

@Composable
fun TudeeBottomNavigationBar(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    items: List<TudeeBottomNavItem> = TudeeBottomNavItems.items,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route


    NavigationBar(
        modifier = modifier,
        tonalElevation = 0.dp,
        containerColor = TudeeTheme.colors.surfaceHigh
    ) {
        items.forEach { item ->
            val isSelected = item.route == currentRoute
            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    if (item.route != currentRoute) {
                        navController.navigate(item.route) {
                            launchSingleTop = true
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            restoreState = true
                        }
                    }
                },
                icon = {
                    if (isSelected) {
                        Box(
                            modifier = Modifier
                                .size(42.dp)
                                .background(
                                    color = TudeeTheme.colors.primaryVariant,
                                    shape = TudeeTheme.shapes.small
                                ), contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                modifier = Modifier.size(21.5.dp),
                                painter = painterResource(item.selectedIcon),
                                contentDescription = item.contentDescription,
                                tint = Color.Unspecified
                            )
                        }
                    } else {
                        Icon(
                            modifier = Modifier.size(21.5.dp),
                            painter = painterResource(item.unselectedIcon),
                            contentDescription = item.contentDescription,
                            tint = TudeeTheme.colors.hint
                        )
                    }
                }, colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}


@ThemePreviews
@Composable
fun PreviewTestScreen() {
    val navController = rememberNavController()
    TudeeTheme {
        Scaffold(
            bottomBar = {
                TudeeBottomNavigationBar(navController = navController)
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Routes.HOME,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(Routes.HOME) { NavigationTestScreen(TudeeTheme.colors.primary) }
                composable(Routes.TASKS) { NavigationTestScreen(TudeeTheme.colors.purpleAccent) }
                composable(Routes.CATEGORIES) { NavigationTestScreen(TudeeTheme.colors.emojiTint) }
            }
        }
    }
}