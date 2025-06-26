package com.london.tudee.presentation.screens.task.task_modify

import androidx.annotation.StringRes
import com.london.tudee.domain.entities.Category
import com.london.tudee.domain.entities.Priority

data class TaskModifyUiState(
    val taskId: Int? = null,
    val title: String = "",
    val description: String = "",
    val selectedDate: Long? = null,
    val selectedPriority: Priority = Priority.LOW,
    val selectedCategory: Category? = null,
    val categoryIcons: List<String> = listOf(),
    val categories: List<Category> = emptyList(),
    val showBottomSheet: Boolean = false,
    val showDatePicker: Boolean = false,
    val isLoading: Boolean = false,
    val isFormValid: Boolean = false,
    @StringRes val stateMessage: Int? = null,
    val isEditMode: Boolean = false,
)