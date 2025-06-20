package com.london.tudee.presentation.navigation

import androidx.annotation.StringRes
import com.london.tudee.R

sealed class Screen(val route: String) {
    object Onboarding : Screen("onboarding")
    object Main : Screen("main")

    object Home : Screen("home")
    object Tasks : Screen("tasks")
    object Categories : Screen("categories")

    object TaskTab : Screen("tasks/{tab}") {
        fun createRoute(tab: TaskTabStatus) = "tasks/${tab.value}"
    }

    object CategoryDetails : Screen("categoryDetails/{categoryId}") {
        fun createRoute(categoryId: Int) = "categoryDetails/$categoryId"
    }
}
enum class TaskTabStatus(val value: String, @StringRes val labelRes: Int) {
    TODO("todo",R.string.To_Do),
    IN_PROGRESS("in_progress", R.string.In_Progress),
    DONE("done", R.string.Done);

    companion object {
        fun fromValue(value: String): TaskTabStatus = TaskTabStatus.entries.first { it.value == value }
    }
}
