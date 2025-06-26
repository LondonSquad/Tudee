package com.london.tudee.presentation.screens.category.category_details

import androidx.annotation.StringRes
import com.london.tudee.domain.entities.Category
import com.london.tudee.domain.entities.Task

data class CategoryDetailsUiState(
    val isLoading: Boolean = true,
    val isEdited: Boolean = false,
    val isDeleted: Boolean = false,
    val categoryDeleted: Boolean = false,
    val isEditBottomSheetVisible: Boolean = false,
    val isDeleteBottomSheetVisible: Boolean = false,
    val showSuccessMessage: Boolean = false,
    val showErrorMessage: Boolean = false,
    val errorMessage: String? = null,
    @StringRes val stateMessage: Int? = null,
    val allTasks: List<Task> = listOf(),
    val doneTasks: List<Task> = listOf(),
    val inProgressTasks: List<Task> = listOf(),
    val toDoTasks: List<Task> = listOf(),
    val category: Category = Category(
        id = 0,
        title = "",
        iconRes = "",
        isDefault = false,
        taskCount = 0
    )
)