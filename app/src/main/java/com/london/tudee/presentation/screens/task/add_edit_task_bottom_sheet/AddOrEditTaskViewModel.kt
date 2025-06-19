package com.london.tudee.presentation.screens.task.add_edit_task_bottom_sheet


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.london.tudee.domain.entities.Priority
import com.london.tudee.domain.entities.Task
import com.london.tudee.domain.entities.TaskStatus
import com.london.tudee.domain.mapper.convertFromTimeStampToDate
import com.london.tudee.domain.services.CategoryService
import com.london.tudee.domain.services.TaskService
import com.london.tudee.presentation.screens.categories.CategoryUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Date

class AddOrEditTaskViewModel(
    private val taskService: TaskService,
    private val categoryService: CategoryService
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddOrEditTaskUiState())
    val uiState: StateFlow<AddOrEditTaskUiState> = _uiState.asStateFlow()

    fun updateTitle(title: String) {
        _uiState.value = _uiState.value.copy(
            title = title,
            isFormValid = validateForm(title)
        )
    }

    fun updateDescription(description: String) {
        _uiState.value = _uiState.value.copy(
            description = description
        )
    }

    fun updateSelectedDate(date: Long?) {
        _uiState.value = _uiState.value.copy(selectedDate = date)
    }

    fun updateSelectedPriority(priority: Priority) {
        _uiState.value = _uiState.value.copy(selectedPriority = priority)
    }

    fun updateSelectedCategory(category: CategoryUiModel?) {
        _uiState.value = _uiState.value.copy(selectedCategory = category)
    }

    fun showDatePicker() {
        _uiState.value = _uiState.value.copy(showDatePicker = true)
    }

    fun hideDatePicker() {
        _uiState.value = _uiState.value.copy(showDatePicker = false)
    }

    fun showBottomSheet() {
        _uiState.value = _uiState.value.copy(showBottomSheet = true)
    }

    fun hideBottomSheet() {
        _uiState.value = _uiState.value.copy(showBottomSheet = false)
    }

    fun saveTask() {
        val currentState = _uiState.value
        if (!currentState.isFormValid) return

        viewModelScope.launch {
            _uiState.value = currentState.copy(isLoading = true)

            try {
                val task = Task(
                    id = currentState.taskId ?: 0,
                    title = currentState.title,
                    description = currentState.description,
                    priority = currentState.selectedPriority,
                    taskStatus = TaskStatus.TODO,
                    categoryId = taskService.getById(currentState.taskId ?: 0).categoryId,
                    timeStamp = currentState.selectedDate?.convertFromTimeStampToDate() ?: Date(),
                )

                if (_uiState.value.isEditMode) {
                    taskService.edit(task)
                    _uiState.value = _uiState.value.copy(successMessage = "Edit Task Successfully")
                } else {
                    taskService.add(task)
                    _uiState.value = _uiState.value.copy(successMessage = "Add Task Successfully")
                }
            } catch (_: Exception) {
                _uiState.value = _uiState.value.copy(errorMessage = "Some error happened")
            } finally {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }

    fun clearForm() {
        _uiState.value = AddOrEditTaskUiState()
    }

    private fun validateForm(title: String): Boolean {
        return title.isNotBlank()
    }

//    fun loadTask(taskId: Int?) {
//        if (taskId == null) return
//
//        viewModelScope.launch {
//            _uiState.value = _uiState.value.copy(isLoading = true)
//
//            try {
//                val task = taskService.getById(taskId)
//                val category = categoryService.getById(task.categoryId)
//                 _uiState.value = _uiState.value.copy(
//                     title = task.title,
//                     description = task.description,
//                     selectedPriority = task.priority,
//                     selectedCategory = task.category
//                 )
//            } catch (e: Exception) {
//                // Handle error
//            } finally {
//                _uiState.value = _uiState.value.copy(isLoading = false)
//            }
//        }
//    }
}