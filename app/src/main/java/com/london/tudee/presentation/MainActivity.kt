package com.london.tudee.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.london.tudee.presentation.components.bottom_navigation_bar.TudeeBottomNavItems
import com.london.tudee.presentation.components.bottom_navigation_bar.TudeeBottomNavigationBar
import com.london.tudee.presentation.design_system.theme.ThemePreviews
import com.london.tudee.presentation.design_system.theme.TudeeTheme
import com.london.tudee.presentation.navigation.Screen
import com.london.tudee.presentation.navigation.tudeeNavGraph
import com.london.tudee.presentation.screens.home.HomeScreen
import com.london.tudee.presentation.screens.onboarding.OnBoardingHorizontalPager
import com.london.tudee.presentation.screens.onboarding.OnBoardingViewModel
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TudeeTheme {

                val navController = rememberNavController()
                val currentBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = currentBackStackEntry?.destination?.route
                val showBottomBar = TudeeBottomNavItems.items.any { it.route == currentRoute }

//
//              val currentScreen = currentBackStackEntry?.toRoute<Screen.Home>()
//                val showBottomBar = when (currentScreen) {
//                    is Screen.Home,
//                    is Screen.Tasks,
//                    is Screen.Categories -> true
//                    else -> false
//                }

                val startDestination = Screen.Onboarding
                Scaffold(
                    bottomBar = {
                        if (showBottomBar) {
                            TudeeBottomNavigationBar(
                                navController = navController,
                                modifier = Modifier.zIndex(0f)
                            )
                        }
                    }
                ) { //padding ->
                    NavHost(
                        navController = navController,
                        startDestination = startDestination,
                    //    modifier = Modifier.padding(padding)
                    ) {
                        tudeeNavGraph(navController)
                    }
                }
            }
        }
    }
}
