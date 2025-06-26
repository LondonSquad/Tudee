package com.london.tudee.presentation.screens.category

import com.london.tudee.domain.entities.Category

data class CategoriesUiState(
    val categories: List<Category> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val showBottomSheet: Boolean = false
)
