package com.london.tudee.presentation.screens.category.create_category

data class CreateCategoryUiState(
    val isLoading: Boolean = false,
    val isDeleted: Boolean = false,
    val errorMessage: String? = null
)