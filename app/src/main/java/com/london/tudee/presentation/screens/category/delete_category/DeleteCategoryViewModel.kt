package com.london.tudee.presentation.screens.category.delete_category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.london.tudee.domain.entities.Category
import com.london.tudee.domain.services.CategoryService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class DeleteCategoryScreenViewModel(
    private val categoryService: CategoryService
) : ViewModel() {

    private val _deleteState = MutableStateFlow(DeleteCategoryUiState())
    val deleteState = _deleteState.asStateFlow()

    fun deleteCategory(category: Category) {
        viewModelScope.launch {
            _deleteState.value = _deleteState.value.copy(isLoading = true)
            runCatching {
                categoryService.delete(category)
                _deleteState.value = _deleteState.value.copy(isDeleted = true, isLoading = false)
            }.onFailure {
                _deleteState.value = _deleteState.value.copy(
                    isLoading = false,
                    errorMessage = it.message
                )
            }
        }
    }
}

data class DeleteCategoryUiState(
    val isLoading: Boolean = false,
    val isDeleted: Boolean = false,
    val errorMessage: String? = null
)