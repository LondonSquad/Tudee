package com.london.tudee.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.london.tudee.presentation.screens.categories.CategoriesScreen

fun NavGraphBuilder.categoriesRoute(navController: NavController){
    composable<Screen.Categories>{
        CategoriesScreen(
            onCategoryClick = { index ->
                navController.navigateToCategoryDetailsScreen(index)
            },
        )
    }
}

fun NavController.navigateToCategoriesScreen(){
    navigate(route = Screen.Categories)
}