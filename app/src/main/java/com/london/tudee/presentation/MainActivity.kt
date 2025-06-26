package com.london.tudee.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.london.tudee.R
import com.london.tudee.domain.services.AppPreferencesService
import com.london.tudee.presentation.components.bottom_navigation_bar.TudeeBottomNavigationBar
import com.london.tudee.presentation.components.snackbar.ObserveAsEvent
import com.london.tudee.presentation.components.snackbar.SnackBarCard
import com.london.tudee.presentation.components.snackbar.SnackbarController
import com.london.tudee.presentation.design_system.theme.TudeeTheme
import com.london.tudee.presentation.navigation.Screen
import com.london.tudee.presentation.navigation.tudeeNavGraph
import com.london.tudee.presentation.utils.RememberedEffect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    private val appPreferencesService: AppPreferencesService by inject()

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val isOnboardingShown = appPreferencesService.hasOnboardingBeenShown
        val isDarkMode by appPreferencesService.isDarkModeEnabled

        setContent {
            val isSystemDarkMode = isSystemInDarkTheme()

            RememberedEffect(Unit) {
                if (isDarkMode == null)
                    appPreferencesService.setDarkModeEnabled(isSystemDarkMode)
            }

            TudeeTheme(isDarkMode = isDarkMode ?: isSystemDarkMode) {
                val navController = rememberNavController()
                val currentBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = currentBackStackEntry?.destination?.route
                val showBottomBar = when {
                    currentRoute?.contains("Screen.Home") == true -> true
                    currentRoute?.contains("Screen.Tasks") == true -> true
                    currentRoute?.contains("Screen.Categories") == true -> true
                    else -> false
                }
                val startDestination = if (isOnboardingShown) {
                    Screen.Home
                } else {
                    Screen.Onboarding
                }
                Scaffold(
                    bottomBar = {
                        if (showBottomBar) {
                            TudeeBottomNavigationBar(
                                navController = navController,
                                modifier = Modifier.zIndex(0f)
                            )
                        }
                    }
                ) { paddingValues ->
                    NavHost(
                        modifier = Modifier
                            .padding(bottom = paddingValues.calculateBottomPadding()),
                        navController = navController,
                        startDestination = startDestination,
                    ) {
                        tudeeNavGraph(navController)
                    }
                }
            }
            SnackbarHandler()
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