package com.london.tudee.presentation.screens.categories

import android.net.Uri
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.london.tudee.R
import com.london.tudee.domain.entities.Category
import com.london.tudee.domain.services.CategoryService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class CreateCategoryUiState(
    val categoryName: String = "",
    val categoryNameAr: String = "",
    val selectedImageUri: Uri? = null,
    val selectedColor: Color = Color.Blue,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSuccess: Boolean = false
)

class CreateCategoryViewModel(
    private val categoryService: CategoryService
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateCategoryUiState())
    val uiState = _uiState.asStateFlow()

    fun updateCategoryName(name: String) {
        _uiState.update { it.copy(categoryName = name) }
    }

    fun updateCategoryNameAr(nameAr: String) {
        _uiState.update { it.copy(categoryNameAr = nameAr) }
    }

    fun updateSelectedImage(uri: Uri?) {
        _uiState.update { it.copy(selectedImageUri = uri) }
    }

    fun updateSelectedColor(color: Color) {
        _uiState.update { it.copy(selectedColor = color) }
    }

    fun createCategory() {
        val currentState = _uiState.value
        
        if (currentState.categoryName.isBlank()) {
            _uiState.update { it.copy(errorMessage = "Category name is required") }
            return
        }

        _uiState.update { it.copy(isLoading = true, errorMessage = null) }

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val newCategory = Category(
                    id = 0, // Auto-generated
                    name = currentState.categoryName,
                    arName = currentState.categoryNameAr.ifBlank { currentState.categoryName },
                    iconRes = R.drawable.ic_entertainment, // Default icon, could be customized
                    isDefault = false,
                    taskCount = 0,
                    tint = currentState.selectedColor
                )

                categoryService.add(newCategory)
                
                _uiState.update { 
                    it.copy(
                        isLoading = false, 
                        isSuccess = true,
                        errorMessage = null
                    ) 
                }
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(
                        isLoading = false, 
                        errorMessage = e.message ?: "Failed to create category"
                    ) 
                }
            }
        }
    }

    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }

    fun resetState() {
        _uiState.update { CreateCategoryUiState() }
    }
}