package com.london.tudee.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.london.tudee.presentation.screens.category.category_details.CategoryDetailsScreen


fun NavGraphBuilder.categoryDetailsRoute(navController: NavController) {
    composable<Screen.CategoryDetails> { backStackEntry ->
        val entry = backStackEntry.toRoute<Screen.CategoryDetails>()
        val categoryId = entry.categoryId
        CategoryDetailsScreen(
            categoryId = categoryId,
            onBackClick = {
                navController.navigateUp()
            }
        )
    }
}

fun NavController.navigateToCategoryDetailsScreen(categoryId: Int) {
    navigate(
        route = Screen.CategoryDetails(
            categoryId = categoryId
        )
    )
}