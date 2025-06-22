package com.london.tudee.presentation.screens.task.add_edit_task_bottom_sheet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.london.tudee.R
import com.london.tudee.domain.entities.Category
import com.london.tudee.domain.entities.Priority
import com.london.tudee.domain.entities.Task
import com.london.tudee.domain.entities.TaskStatus
import com.london.tudee.domain.services.CategoryService
import com.london.tudee.domain.services.TaskService
import com.london.tudee.presentation.utils.formatDate
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant


class AddOrEditTaskViewModel(
    private val taskService: TaskService,
    private val categoryService: CategoryService
) : ViewModel(), AddOrEditInteraction {

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
                            selectedCategory = currentState.selectedCategory
                        )
                    }
                    validateForm()
                }
            } catch (_: Exception) {
                _uiState.update { it.copy(stateMessage = R.string.some_error_happened) }
            }
        }
    }

    override fun initializeForEdit(taskId: Int) {
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
                        selectedDate = task.timeStamp.toEpochMilliseconds(),
                        selectedPriority = task.priority,
                        selectedCategory = category,
                        isEditMode = true,
                        isLoading = false,
                        showBottomSheet = true
                    )
                }
                validateForm()
            } catch (_: Exception) {
                _uiState.update {
                    it.copy(
                        stateMessage = R.string.some_error_happened, isLoading = false
                    )
                }
            }
        }
    }

    override fun updateTitle(title: String) {
        _uiState.update { it.copy(title = title) }
        validateForm()
    }

    override fun updateDescription(description: String) {
        _uiState.update { it.copy(description = description) }
    }

    override fun updateSelectedDate(date: Long) {
        _uiState.update { it.copy(selectedDate = date) }
        validateForm()
    }

    override fun updateSelectedPriority(priority: Priority) {
        _uiState.update { it.copy(selectedPriority = priority) }
        validateForm()
    }

    override fun updateSelectedCategory(category: Category) {
        _uiState.update { it.copy(selectedCategory = category) }
        validateForm()
    }

    override fun showDatePicker() {
        _uiState.update { it.copy(showDatePicker = true) }
    }

    override fun hideDatePicker() {
        _uiState.update { it.copy(showDatePicker = false) }
    }

    override fun showBottomSheet() {
        _uiState.update { it.copy(showBottomSheet = true) }
    }

    override fun hideBottomSheet() {
        _uiState.update {
            it.copy(
                showBottomSheet = false,
            )
        }

        viewModelScope.launch {
            delay(500)
            _uiState.update {
                it.copy(
                    title = "",
                    description = "",
                    selectedDate = null,
                    selectedPriority = Priority.LOW,
                    selectedCategory = it.categories.firstOrNull(),
                    stateMessage = null,
                    isEditMode = false,
                    taskId = null
                )
            }
            validateForm()
        }
    }

    override fun saveTask() {
        val currentState = _uiState.value

        if (!currentState.isFormValid) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                val task = Task(
                    id = currentState.taskId ?: 0,
                    title = currentState.title.trim(),
                    description = currentState.description.trim(),
                    taskStatus = TaskStatus.TODO,
                    priority = currentState.selectedPriority,
                    categoryId = currentState.selectedCategory?.id ?: 1,
                    timeStamp =  currentState.selectedDate?.let { Instant.fromEpochMilliseconds(it) } ?: Clock.System.now()
                )
                if (currentState.isEditMode) {
                    taskService.edit(task)
                } else {
                    taskService.add(task)

                }

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        stateMessage = if (currentState.isEditMode) R.string.edit_task_successfully
                        else R.string.add_task_successfully,
                        showBottomSheet = false
                    )
                }

            } catch (_: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false, stateMessage = R.string.some_error_happened
                    )
                }
            }
        }
    }

    private fun validateForm() {
        _uiState.update { currentState ->
            currentState.copy(
                isFormValid = currentState.title.isNotBlank()
                        && currentState.selectedDate != null
                        && currentState.selectedCategory != null
            )
        }
    }

    override fun clearMessages() {
        _uiState.update {
            it.copy(
                stateMessage = null
            )
        }
    }
}