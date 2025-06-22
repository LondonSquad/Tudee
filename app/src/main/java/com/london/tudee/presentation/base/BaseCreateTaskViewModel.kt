package com.london.tudee.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.london.tudee.R
import com.london.tudee.domain.entities.Category
import com.london.tudee.domain.entities.Priority
import com.london.tudee.domain.entities.Task
import com.london.tudee.domain.entities.TaskStatus
import com.london.tudee.domain.services.CategoryService
import com.london.tudee.domain.services.TaskService
import com.london.tudee.presentation.screens.task.add_edit_task_bottom_sheet.AddOrEditTaskUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

abstract class BaseCreateTaskViewModel(
    private val taskService: TaskService,
    private val categoryService: CategoryService
) : ViewModel(), BaseCreateTaskInteractions {
    private val _taskUiState = MutableStateFlow(AddOrEditTaskUiState())
    val taskUiState = _taskUiState.asStateFlow()

    init {
        loadCategories()
    }

    final override fun loadCategories() {
        viewModelScope.launch {
            try {
                categoryService.getAll().collect { categories ->
                    _taskUiState.update { currentState ->
                        currentState.copy(
                            categories = categories,
                            selectedCategory = currentState.selectedCategory
                        )
                    }
                    validateForm()
                }
            } catch (_: Exception) {
                _taskUiState.update { it.copy(errorMessage = R.string.some_error_happened) }
            }
        }
    }

    override fun updateTitle(title: String) {
        _taskUiState.update { it.copy(title = title) }
        validateForm()
    }

    override fun updateDescription(description: String) {
        _taskUiState.update { it.copy(description = description) }
    }

    override fun updateSelectedDate(date: Long) {
        _taskUiState.update { it.copy(selectedDate = date) }
        validateForm()
    }

    override fun updateSelectedPriority(priority: Priority) {
        _taskUiState.update { it.copy(selectedPriority = priority) }
        validateForm()
    }

    override fun updateSelectedCategory(category: Category) {
        _taskUiState.update { it.copy(selectedCategory = category) }
        validateForm()
    }

    override fun showDatePicker() {
        _taskUiState.update { it.copy(showDatePicker = true) }
    }

    override fun hideDatePicker() {
        _taskUiState.update { it.copy(showDatePicker = false) }
    }

    override fun showBottomSheet() {
        _taskUiState.update { it.copy(showBottomSheet = true) }
    }

    override fun hideBottomSheet() {
        _taskUiState.update {
            it.copy(
                showBottomSheet = false,
            )
        }

        viewModelScope.launch {
            delay(500)
            _taskUiState.update {
                it.copy(
                    title = "",
                    description = "",
                    selectedDate = null,
                    selectedPriority = Priority.LOW,
                    selectedCategory = it.categories.firstOrNull(),
                    successMessage = null,
                    errorMessage = null,
                    isEditMode = false,
                    taskId = null
                )
            }
            validateForm()
        }
    }

    override fun saveTask() {
        val currentState = _taskUiState.value

        if (!currentState.isFormValid) return

        viewModelScope.launch {
            _taskUiState.update { it.copy(isLoading = true) }

            try {
                val task = Task(
                    id = currentState.taskId ?: 0,
                    title = currentState.title.trim(),
                    description = currentState.description.trim(),
                    taskStatus = TaskStatus.TODO,
                    priority = currentState.selectedPriority,
                    categoryId = currentState.selectedCategory?.id ?: 1,
                    timeStamp = currentState.selectedDate?.let { Instant.fromEpochMilliseconds(it) }
                        ?: Clock.System.now()
                )
                if (currentState.isEditMode) {
                    taskService.edit(task)
                } else {
                    taskService.add(task)
                }

                _taskUiState.update {
                    it.copy(
                        isLoading = false,
                        successMessage = if (currentState.isEditMode) R.string.edit_task_successfully
                        else R.string.add_task_successfully,
                        showBottomSheet = false
                    )
                }

            } catch (_: Exception) {
                _taskUiState.update {
                    it.copy(
                        isLoading = false, errorMessage = R.string.some_error_happened
                    )
                }
            }
        }

        clearMessages()
    }

    override fun validateForm() {
        _taskUiState.update { currentState ->
            currentState.copy(
                isFormValid = currentState.title.isNotBlank()
                        && currentState.selectedDate != null &&
                        currentState.selectedCategory != null
            )
        }
    }

    override fun clearMessages() {
        _taskUiState.update {
            it.copy(
                successMessage = null, errorMessage = null
            )
        }
    }
}