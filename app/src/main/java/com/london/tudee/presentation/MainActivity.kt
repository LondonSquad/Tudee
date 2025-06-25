package com.london.tudee.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.london.tudee.presentation.components.bottom_navigation_bar.TudeeBottomNavigationBar
import com.london.tudee.presentation.design_system.theme.TudeeTheme
import com.london.tudee.presentation.navigation.Screen
import com.london.tudee.presentation.navigation.tudeeNavGraph

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TudeeTheme {
                val navController = rememberNavController()
                val currentBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = currentBackStackEntry?.destination?.route
                val showBottomBar = when {
                    currentRoute?.contains("Screen.Home") == true -> true
                    currentRoute?.contains("Screen.Tasks") == true -> true
                    currentRoute?.contains("Screen.Categories") == true -> true
                    else -> false
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
                        startDestination = Screen.Onboarding,
                    ) {
                        tudeeNavGraph(navController)
                    }
                }
            }
        }
    }
}