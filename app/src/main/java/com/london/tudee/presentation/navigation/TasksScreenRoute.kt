package com.london.tudee.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.london.tudee.presentation.screens.tasks.TasksScreen

fun NavGraphBuilder.tasksRoute(navController: NavController){
    composable<Screen.Tasks> { bacStackEntry ->
        val entry = bacStackEntry.toRoute<Screen.Tasks>()
        val tabIndex = entry.tabIndex
        TasksScreen(
            initialTabIndex = tabIndex ?: 0
        )
    }
}

fun NavController.navigateToTasksScreen(tabIndex: Int) {
    navigate(Screen.Tasks(tabIndex = tabIndex))
}
