package com.london.tudee.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.london.tudee.presentation.screens.home.HomeScreen

fun NavGraphBuilder.homeRoute(navController: NavController){
    composable<Screen.Home>{
        HomeScreen(
            onArrowClicked = { statusIndex ->
                navController.navigateToTasksScreen(statusIndex)
            }
        )
    }
}

fun NavController.navigateToHomeScreen(popUp: Boolean = false){
    navigate(route = Screen.Home){
        if (popUp) {
            popUpTo(Screen.Onboarding) {
                inclusive = true
            }
        }
        launchSingleTop = true
    }
}
