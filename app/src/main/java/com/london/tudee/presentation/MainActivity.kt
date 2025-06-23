package com.london.tudee.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.zIndex
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.london.tudee.presentation.components.bottom_navigation_bar.TudeeBottomNavItems
import com.london.tudee.presentation.components.bottom_navigation_bar.TudeeBottomNavigationBar
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.london.tudee.R
import com.london.tudee.presentation.components.snackbar.ObserveAsEvent
import com.london.tudee.presentation.components.snackbar.SnackBarCard
import com.london.tudee.presentation.components.snackbar.SnackbarController
import com.london.tudee.presentation.design_system.theme.TudeeTheme
import com.london.tudee.presentation.navigation.Screen
import com.london.tudee.presentation.navigation.tudeeNavGraph
import com.london.tudee.presentation.screens.categories.CategoriesScreen
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TudeeTheme {
                CategoriesScreen(onCategoryClick = {})
                val navController = rememberNavController()
                val currentBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = currentBackStackEntry?.destination?.route
                val showBottomBar = TudeeBottomNavItems.items.any { it.route == currentRoute }

//              val currentScreen = currentBackStackEntry?.toRoute<Screen.Home>()
//                val showBottomBar = when (currentScreen) {
//                    is Screen.Home,
//                    is Screen.Tasks,
//                    is Screen.Categories -> true
//                    else -> false
//                }

                Scaffold(
                    bottomBar = {
                        if (showBottomBar) {
                            TudeeBottomNavigationBar(
                                navController = navController,
                                modifier = Modifier.zIndex(0f)
                            )
                        }
                    }
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Onboarding,
                    ) {
                        tudeeNavGraph(navController)
                    }
                }
            }
           // SnackbarHandler()
        }
    }
}

@Composable
fun SnackbarHandler() {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    ObserveAsEvent(
        flow = SnackbarController.event,
        onEvent = { event ->
            coroutineScope.launch {
                snackbarHostState.currentSnackbarData?.dismiss()
                snackbarHostState.showSnackbar(
                    message = event.message,
                    duration = SnackbarDuration.Short
                )
            }
        }
    )

    SnackbarHost(
        hostState = snackbarHostState,
        snackbar = { snackbarData ->
            val (iconRes, iconTint) = when {
                snackbarData.visuals.message.contains("success", ignoreCase = true) ->
                    Pair(R.drawable.snack_bar_container, TudeeTheme.colors.greenAccent)

                snackbarData.visuals.message.contains("error", ignoreCase = true) ->
                    Pair(R.drawable.snack_bar_error, TudeeTheme.colors.error)

                else ->
                    Pair(R.drawable.snack_bar_error, TudeeTheme.colors.error)
            }

            SnackBarCard(
                message = snackbarData.visuals.message,
                icon = painterResource(id = iconRes),
                iconTint = iconTint,
                iconBackgroundColor = TudeeTheme.colors.surface,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 48.dp)
            )

        }
    )
}