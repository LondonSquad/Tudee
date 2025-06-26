package com.london.tudee.presentation.components.navigation_bar_bottom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
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
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.london.tudee.presentation.design_system.theme.ThemePreviews
import com.london.tudee.presentation.design_system.theme.TudeeTheme
import com.london.tudee.presentation.navigation.Screen
import com.london.tudee.presentation.navigation.tudeeNavGraph

@Composable
fun TudeeBottomNavigationBar(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    items: List<TudeeBottomNavItem> = TudeeBottomNavItems.items,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        modifier = modifier,
        tonalElevation = 0.dp,
        containerColor = TudeeTheme.colors.surfaceHigh,
        windowInsets = WindowInsets(0)
    ) {
        items.forEach { item ->
            val isSelected = when (item.screen) {
                is Screen.Home -> currentRoute?.contains("Screen.Home") == true
                is Screen.Tasks -> currentRoute?.contains("Screen.Tasks") == true
                is Screen.Categories -> currentRoute?.contains("Screen.Categories") == true
                else -> false
            }

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    if (!isSelected) {
                        navController.navigate(item.screen) {
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
                },
                colors = NavigationBarItemDefaults.colors(
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
                startDestination = Screen.Home,
                modifier = Modifier.padding(innerPadding)
            ) {
                tudeeNavGraph(navController)
            }
        }
    }
}