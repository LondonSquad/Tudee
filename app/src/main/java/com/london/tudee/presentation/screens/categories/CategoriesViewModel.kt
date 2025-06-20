package com.london.tudee.presentation.screens.categories

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.london.tudee.domain.entities.Category
import com.london.tudee.domain.services.CategoryService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class CategoriesUiState(
    val categories: List<Category> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class CategoriesViewModel(
    private val categoryService: CategoryService
) : ViewModel() {

    private val _uiState = MutableStateFlow(CategoriesUiState())
    val uiState = _uiState.asStateFlow()

    // Keep the old state for backward compatibility
    val categoryUiState = _uiState.asStateFlow().let { stateFlow ->
        MutableStateFlow(stateFlow.value.categories).apply {
            viewModelScope.launch {
                stateFlow.collect { state ->
                    update { state.categories }
                }
            }
        }.asStateFlow()
    }

    init {
        getCategories()
    }

    private fun getCategories() {
        _uiState.update { it.copy(isLoading = true, errorMessage = null) }
        
        viewModelScope.launch(Dispatchers.IO) {
            categoryService.getAll()
                .catch { throwable ->
                    Log.e("CategoriesViewModel", "Error loading categories", throwable)
                    _uiState.update { 
                        it.copy(
                            isLoading = false, 
                            errorMessage = throwable.message ?: "Failed to load categories"
                        ) 
                    }
                }
                .collect { categories ->
                    Log.d("CategoriesViewModel", "Loaded ${categories.size} categories")
                    _uiState.update { 
                        it.copy(
                            categories = categories,
                            isLoading = false,
                            errorMessage = null
                        ) 
                    }
                }
        }
    }

    fun refreshCategories() {
        getCategories()
    }

    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }
}

