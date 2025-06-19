package com.london.tudee.presentation.screens.task.add_edit_task_bottom_sheet

import com.london.tudee.domain.entities.Priority
import com.london.tudee.presentation.screens.categories.CategoryUiModel

data class AddOrEditTaskUiState(
    val title: String = "",
    val description: String = "",
    val selectedDate: Long? = null,
    val selectedPriority: Priority = Priority.HIGH,
    val selectedCategory: CategoryUiModel? = null,
    val isLoading: Boolean = false,
    val isFormValid: Boolean = false,
    val showDatePicker: Boolean = false,
    val isEditMode: Boolean = false,
    val taskId: Int = 4,
    val successMessage: String? = null,
    val errorMessage: String? = null,
    var showBottomSheet: Boolean = true,
)