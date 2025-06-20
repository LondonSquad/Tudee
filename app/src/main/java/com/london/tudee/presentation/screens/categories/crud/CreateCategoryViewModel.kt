package com.london.tudee.presentation.screens.categories.crud

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.london.tudee.domain.entities.Category
import com.london.tudee.domain.services.CategoryService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CreateCategoryScreenViewModel(
    private val categoryService: CategoryService
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateCategoryUiState())
    val uiState = _uiState.asStateFlow()


    fun createCategory(
        category: Category
    ) {
        _uiState.value = _uiState.value.copy(isLoading = true)
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                categoryService.add(category)
                _uiState.value = _uiState.value.copy(isDeleted = true, isLoading = false)

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message
                )
            }
        }
    }

}


data class CreateCategoryUiState(
    val isLoading: Boolean = false,
    val isDeleted: Boolean = false,
    val errorMessage: String? = null
)