package com.london.tudee.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Screen {
    @Serializable
    object Home : Screen

    @Serializable
    data class Tasks(val tabIndex: Int? = null) : Screen

    @Serializable
    object Categories : Screen

    @Serializable
    data class CategoryDetails(val categoryId: Int) : Screen

    @Serializable
    object Onboarding : Screen
}

