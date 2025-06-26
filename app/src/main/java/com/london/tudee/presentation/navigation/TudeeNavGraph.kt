package com.london.tudee.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

fun NavGraphBuilder.tudeeNavGraph(navController: NavHostController) {
    onboardingRoute(navController)
    homeRoute(navController)
    tasksRoute(navController)
    categoriesRoute(navController)
    categoryDetailsRoute(navController)
}