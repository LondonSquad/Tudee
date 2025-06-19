package com.london.tudee.presentation.screens.task.add_edit_task_bottom_sheet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.london.tudee.domain.entities.Category
import com.london.tudee.domain.entities.Priority
import com.london.tudee.domain.entities.Task
import com.london.tudee.domain.entities.TaskStatus
import com.london.tudee.domain.services.CategoryService
import com.london.tudee.domain.services.TaskService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import java.util.Date


class AddOrEditTaskViewModel(
    private val taskService: TaskService,
    private val categoryService: CategoryService
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddOrEditTaskUiState())
    val uiState: StateFlow<AddOrEditTaskUiState> = _uiState.asStateFlow()

    init {
        loadCategories()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            try {
                categoryService.getAll().collect { categories ->
                    _uiState.update { currentState ->
                        currentState.copy(
                            categories = categories,
                            // Set default category if none is selected
                            selectedCategory = currentState.selectedCategory ?: categories.firstOrNull()
                        )
                    }
                    validateForm()
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(errorMessage = e.message) }
            }
        }
    }

    fun initializeForEdit(taskId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val task = taskService.getById(taskId)
                val category = categoryService.getById(task.categoryId)

                _uiState.update { currentState ->
                    currentState.copy(
                        taskId = task.id,
                        title = task.title,
                        description = task.description,
                        selectedDate = task.timeStamp.time,
                        selectedPriority = task.priority,
                        selectedCategory = category,
                        isEditMode = true,
                        isLoading = false,
                        showBottomSheet = true
                    )
                }
                validateForm()
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        errorMessage = e.message,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun updateTitle(title: String) {
        _uiState.update { it.copy(title = title) }
        validateForm()
    }

    fun updateDescription(description: String) {
        _uiState.update { it.copy(description = description) }
        // No need to validate form as description is optional
    }

    fun updateSelectedDate(date: Long) {
        _uiState.update { it.copy(selectedDate = date) }
        validateForm()
    }

    fun updateSelectedPriority(priority: Priority) {
        _uiState.update { it.copy(selectedPriority = priority) }
        validateForm()
    }

    fun updateSelectedCategory(category: Category) {
        _uiState.update { it.copy(selectedCategory = category) }
        validateForm()
    }

    fun showDatePicker() {
        _uiState.update { it.copy(showDatePicker = true) }
    }

    fun hideDatePicker() {
        _uiState.update { it.copy(showDatePicker = false) }
    }

    fun showBottomSheet() {
        _uiState.update { it.copy(showBottomSheet = true) }
    }

    fun hideBottomSheet() {
        _uiState.update {
            it.copy(
                showBottomSheet = false,
                // Don't clear messages immediately so they can be shown
            )
        }

        // Reset form after a delay to allow snackbar to show
        viewModelScope.launch {
            delay(500)
            _uiState.update {
                it.copy(
                    // Reset form
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

    fun saveTask() {
        val currentState = _uiState.value

        if (!currentState.isFormValid) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                val task = Task(
                    id = currentState.taskId ?: 0,
                    title = currentState.title.trim(),
                    description = currentState.description.trim(), // Description can be empty
                    taskStatus = TaskStatus.TODO,
                    priority = currentState.selectedPriority,
                    categoryId = currentState.selectedCategory?.id ?: 1,
                    timeStamp = currentState.selectedDate?.let { Date(it) } ?: Date()
                )

                if (currentState.isEditMode) {
                    taskService.edit(task)
                } else {
                    taskService.add(task)
                }

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        successMessage = if (currentState.isEditMode)
                            "Task updated successfully"
                        else
                            "Task added successfully",
                        showBottomSheet = false
                    )
                }

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "An error occurred"
                    )
                }
            }
        }
    }

    private fun validateForm() {
        _uiState.update { currentState ->
            currentState.copy(
                isFormValid = currentState.title.isNotBlank() && // Title is mandatory
                        currentState.selectedDate != null && // Date is mandatory
                        currentState.selectedCategory != null && // Category is mandatory
                        currentState.selectedPriority != null // Priority is mandatory (always has a value)
            )
        }
    }

    fun clearMessages() {
        _uiState.update {
            it.copy(
                successMessage = null,
                errorMessage = null
            )
        }
    }
}