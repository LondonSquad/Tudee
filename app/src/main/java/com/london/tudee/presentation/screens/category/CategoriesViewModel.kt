package com.london.tudee.presentation.screens.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.london.tudee.domain.entities.Category
import com.london.tudee.domain.services.CategoryService
import com.london.tudee.presentation.screens.category.create_category.CreateCategoryUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class CategoriesViewModel(
    private val categoryService: CategoryService
) : ViewModel() {

    private val _uiState = MutableStateFlow(CategoriesUiState())
    val uiState = _uiState.asStateFlow()

    private val _createCategoryUiState = MutableStateFlow(CreateCategoryUiState())
    val createCategoryUiState = _createCategoryUiState.asStateFlow()

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
            categoryService.getAll().catch { throwable ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = throwable.message ?: "Failed to load categories"
                        )
                    }
                }.collect { categories ->
                    _uiState.update {
                        it.copy(
                            categories = categories, isLoading = false, errorMessage = null
                        )
                    }
                }
        }
    }

    fun createCategory(
        category: Category
    ) {
        viewModelScope.launch {
            _createCategoryUiState.value = _createCategoryUiState.value.copy(isLoading = true)
            try {
                categoryService.add(category)
                _createCategoryUiState.value = _createCategoryUiState.value.copy(isDeleted = true, isLoading = false)
                _uiState.value = _uiState.value.copy(createSuccessMessage = "Category created successfully")

            } catch (e: Exception) {
                _createCategoryUiState.value = _createCategoryUiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message
                )
            }
        }
    }
    fun resetMessage(){
        _uiState.update { it.copy(createSuccessMessage = null) }
    }
    fun setShowBottomSheet(show: Boolean) {
        _uiState.update { it.copy(showBottomSheet = show) }
    }
}