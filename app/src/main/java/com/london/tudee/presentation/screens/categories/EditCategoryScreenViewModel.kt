package com.london.tudee.presentation.screens.categories

import android.net.Uri
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.london.tudee.domain.entities.Category
import com.london.tudee.domain.services.CategoryService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class EditCategoryUiState(
    val category: Category? = null,
    val categoryName: String = "",
    val categoryNameAr: String = "",
    val selectedImageUri: Uri? = null,
    val selectedColor: Color = Color.Blue,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSuccess: Boolean = false,
    val isDeleted: Boolean = false
)

class EditCategoryScreenViewModel(
    private val categoryService: CategoryService
) : ViewModel() {

    private val _uiState = MutableStateFlow(EditCategoryUiState())
    val uiState = _uiState.asStateFlow()

    fun loadCategory(categoryId: Int) {
        _uiState.update { it.copy(isLoading = true, errorMessage = null) }
        
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val category = categoryService.getById(categoryId)
                _uiState.update { 
                    it.copy(
                        category = category,
                        categoryName = category.name,
                        categoryNameAr = category.arName,
                        selectedColor = category.tint,
                        isLoading = false
                    ) 
                }
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(
                        isLoading = false, 
                        errorMessage = e.message ?: "Failed to load category"
                    ) 
                }
            }
        }
    }

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

    fun saveCategory() {
        val currentState = _uiState.value
        val category = currentState.category
        
        if (category == null) {
            _uiState.update { it.copy(errorMessage = "Category not loaded") }
            return
        }
        
        if (currentState.categoryName.isBlank()) {
            _uiState.update { it.copy(errorMessage = "Category name is required") }
            return
        }

        _uiState.update { it.copy(isLoading = true, errorMessage = null) }

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val updatedCategory = category.copy(
                    name = currentState.categoryName,
                    arName = currentState.categoryNameAr.ifBlank { currentState.categoryName },
                    tint = currentState.selectedColor
                )

                categoryService.edit(updatedCategory)
                
                _uiState.update { 
                    it.copy(
                        isLoading = false, 
                        isSuccess = true,
                        errorMessage = null,
                        category = updatedCategory
                    ) 
                }
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(
                        isLoading = false, 
                        errorMessage = e.message ?: "Failed to update category"
                    ) 
                }
            }
        }
    }

    fun deleteCategory() {
        val currentState = _uiState.value
        val category = currentState.category
        
        if (category == null) {
            _uiState.update { it.copy(errorMessage = "Category not loaded") }
            return
        }

        _uiState.update { it.copy(isLoading = true, errorMessage = null) }

        viewModelScope.launch(Dispatchers.IO) {
            try {
                categoryService.delete(category)
                
                _uiState.update { 
                    it.copy(
                        isLoading = false, 
                        isDeleted = true,
                        errorMessage = null
                    ) 
                }
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(
                        isLoading = false, 
                        errorMessage = e.message ?: "Failed to delete category"
                    ) 
                }
            }
        }
    }

    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }

    fun resetState() {
        _uiState.update { EditCategoryUiState() }
    }
}
