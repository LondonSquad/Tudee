package com.london.tudee.presentation.screens.category.edit_category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.london.tudee.domain.entities.Category
import com.london.tudee.domain.services.CategoryService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class EditCategoryScreenViewModel(
    private val categoryService: CategoryService
) : ViewModel() {

    private val _uiState = MutableStateFlow(EditCategoryUiState())
    val uiState = _uiState.asStateFlow()

    fun editCategory(
        category: Category
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            runCatching {
                categoryService.edit(category)
                _uiState.value = _uiState.value.copy(isEdited = true, isLoading = false)
            }.onFailure {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = it.message
                )
            }
        }
    }
}

data class EditCategoryUiState(
    val isLoading: Boolean = false,
    val isEdited: Boolean = false,
    val errorMessage: String? = null,
)