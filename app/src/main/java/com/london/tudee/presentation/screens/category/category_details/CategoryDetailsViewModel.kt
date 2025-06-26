package com.london.tudee.presentation.screens.category.category_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.london.tudee.R
import com.london.tudee.domain.entities.Category
import com.london.tudee.domain.entities.TaskStatus
import com.london.tudee.domain.services.CategoryService
import com.london.tudee.domain.services.TaskService
import com.london.tudee.presentation.screens.category.delete_category.DeleteCategoryUiState
import com.london.tudee.presentation.screens.category.edit_category.EditCategoryUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class CategoryDetailsViewModel(
    private val taskService: TaskService,
    private val categoryService: CategoryService,
) : ViewModel(), CategoryDetailsInteractions {

    private val _uiState = MutableStateFlow(CategoryDetailsUiState())
    val uiState = _uiState.asStateFlow()

    private val _editState = MutableStateFlow(EditCategoryUiState())

    private val _deleteState = MutableStateFlow(DeleteCategoryUiState())

    fun initializeWithCategoryId(categoryId: Int) {
        getDoneTasksByCategoryId(categoryId)
        getToDoTasksByCategoryId(categoryId)
        getInProgressTasksByCategoryId(categoryId)
        getCategoryNameById(categoryId)

    }

    override fun getDoneTasksByCategoryId(categoryId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            taskService.getByCategoryIdAndTaskStatus(
                categoryId = categoryId,
                status = TaskStatus.DONE
            ).catch { throwable ->
                _uiState.update {
                    it.copy(isLoading = false, errMessage = throwable.message)
                }
            }.collect { tasks ->
                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        errMessage = null,
                        doneTasks = tasks.map {
                            it.copy(categoryId = it.categoryId)
                        },
                    )
                }
            }
        }
    }

    override fun getInProgressTasksByCategoryId(categoryId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            taskService.getByCategoryIdAndTaskStatus(
                categoryId = categoryId,
                status = TaskStatus.IN_PROGRESS
            ).catch { throwable ->
                _uiState.update {
                    it.copy(isLoading = false, errMessage = throwable.message)
                }
            }.collect { tasks ->
                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        errMessage = null,
                        inProgressTasks = tasks.map {
                            it.copy(categoryId = it.categoryId)
                        }
                    )
                }
            }
        }
    }

    override fun getToDoTasksByCategoryId(categoryId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            taskService.getByCategoryIdAndTaskStatus(
                categoryId = categoryId,
                status = TaskStatus.TODO
            ).catch { throwable ->
                _uiState.update {
                    it.copy(isLoading = false, errMessage = throwable.message)
                }
            }.collect { tasks ->
                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        errMessage = null,
                        toDoTasks = tasks.map {
                            it.copy(categoryId = it.categoryId)
                        }
                    )
                }
            }
        }
    }

    override fun getCategoryNameById(categoryId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val category = categoryService.getById(categoryId)

                _uiState.update {
                    it.copy(
                        category = Category(
                            id = category.id,
                            isDefault = category.isDefault,
                            iconRes = category.iconRes,
                            taskCount = category.taskCount,
                            title = category.title,
                        )
                    )
                }
            } catch (_: Exception) {
                _uiState.update {
                    it.copy(errMessage = "Category not found")
                }
            }
        }
    }

    override fun editCategory(
        category: Category
    ) {
        viewModelScope.launch {
            _editState.value = _editState.value.copy(isLoading = true)
            try {
                categoryService.edit(category)
                _editState.value = _editState.value.copy(isLoading = false)

            } catch (e: Exception) {
                _editState.value = _editState.value.copy(
                    isLoading = false,
                    errorMessage = e.message
                )
            }
        }
    }

    override fun deleteCategory(category: Category) {
        viewModelScope.launch {
            _deleteState.value = _deleteState.value.copy(isLoading = true)
            try {
                categoryService.delete(category)
                _deleteState.value = _deleteState.value.copy(isDeleted = true, isLoading = false)
            } catch (e: Exception) {
                _deleteState.value = _deleteState.value.copy(
                    isLoading = false,
                    errorMessage = e.message
                )
            }
        }
    }

    override fun refreshAfterChange() {
        val categoryId = _uiState.value.category.id
        initializeWithCategoryId(categoryId)
    }

    override fun onCategoryDeleted() {
        _uiState.update {
            it.copy(
                categoryDeleted = true,
                stateMessage = R.string.delete_category_successfully,
                showSuccessMessage = true
            )
        }
    }

    override fun onCategoryEdited() {
        _uiState.update {
            it.copy(
                stateMessage = R.string.edit_category_successfully,
                showSuccessMessage = true
            )
        }
        refreshAfterChange()
    }

    override fun onCategoryEditError() {
        _uiState.update {
            it.copy(
                stateMessage = R.string.some_error_happened,
                showErrorMessage = true
            )
        }
    }

    override fun onCategoryDeleteError() {
        _uiState.update {
            it.copy(
                stateMessage = R.string.some_error_happened,
                showErrorMessage = true
            )
        }
    }

    override fun clearMessages() {
        _uiState.update {
            it.copy(
                stateMessage = null,
                showSuccessMessage = false,
                showErrorMessage = false
            )
        }
    }

    override fun showEditBottomSheet() {
        _uiState.update { it.copy(isEditBottomSheetVisible = true) }
    }

    override fun hideEditBottomSheet() {
        _uiState.update { it.copy(isEditBottomSheetVisible = false) }
    }

    override fun hideDeleteBottomSheet() {
        _uiState.update { it.copy(isDeleteBottomSheetVisible = false) }
    }

    override fun showDeleteBottomSheet() {
        _uiState.update { it.copy(isDeleteBottomSheetVisible = true) }
    }
}