package com.london.tudee.presentation

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import androidx.core.view.WindowCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.london.tudee.domain.services.AppPreferencesService
import com.london.tudee.presentation.components.navigation_bar_bottom.TudeeBottomNavigationBar
import com.london.tudee.presentation.design_system.theme.TudeeTheme
import com.london.tudee.presentation.navigation.Screen
import com.london.tudee.presentation.navigation.tudeeNavGraph
import com.london.tudee.presentation.utils.RememberedEffect
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    private val appPreferencesService: AppPreferencesService by inject()

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.apply {
                hide(WindowInsets.Type.navigationBars())
                systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility =
                android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        }

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
                                modifier = Modifier.zIndex(0f).navigationBarsPadding()
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
        }
    }
}