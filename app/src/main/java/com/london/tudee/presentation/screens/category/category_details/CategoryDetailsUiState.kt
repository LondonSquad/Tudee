package com.london.tudee.presentation.screens.category.category_details

import androidx.annotation.StringRes
import com.london.tudee.domain.entities.Category
import com.london.tudee.domain.entities.Task

data class CategoryDetailsUiState(
    val isLoading: Boolean = true,
    val errMessage: String? = null,
    val allTasks: List<Task> = listOf(),
    val doneTasks: List<Task> = listOf(),
    val inProgressTasks: List<Task> = listOf(),
    val toDoTasks: List<Task> = listOf(),
    val categoryDeleted: Boolean = false,
    val isEditBottomSheetVisible: Boolean = false,
    val isDeleteBottomSheetVisible: Boolean = false,
    @StringRes val stateMessage: Int? = null,
    val showSuccessMessage: Boolean = false,
    val showErrorMessage: Boolean = false,
    val category: Category = Category(
        id = 0,
        title = "",
        iconRes = "",
        isDefault = false,
        taskCount = 0
    )
)