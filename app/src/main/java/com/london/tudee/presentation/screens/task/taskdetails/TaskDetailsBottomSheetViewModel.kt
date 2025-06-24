package com.london.tudee.presentation.screens.task.taskdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.london.tudee.R
import com.london.tudee.domain.entities.TaskStatus
import com.london.tudee.domain.services.CategoryService
import com.london.tudee.domain.services.TaskService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.annotation.KoinViewModel

@KoinViewModel

class TaskDetailsBottomSheetViewModel(
    private val taskService: TaskService, private val categoryService: CategoryService
) : ViewModel() {

    private val _uiState = MutableStateFlow(TaskDetailsBottomSheetUiState())
    val uiState: StateFlow<TaskDetailsBottomSheetUiState> = _uiState

    fun loadTask(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                val task = taskService.getById(id)
                val icon = categoryService.getIconResById(task.categoryId)
                _uiState.update {
                    it.copy(task = task, categoryIcon = icon)
                }
            }.isFailure.also {
                if (it) _uiState.update {state->
                    state.copy(errorMessages = R.string.Something_went_wrong_task_not_found_or_no_category_icon.toString())
                }
            }
        }
    }

    fun onClickMove() {
        viewModelScope.launch {
            val task = uiState.value.task
            val newStatus = when (task.taskStatus) {
                TaskStatus.TODO -> TaskStatus.IN_PROGRESS
                TaskStatus.IN_PROGRESS -> TaskStatus.DONE
                TaskStatus.DONE -> return@launch
            }
            val updatedTask = task.copy(taskStatus = newStatus)
            withContext(Dispatchers.IO) {
                runCatching {
                    taskService.edit(updatedTask)
                }.isFailure.also {
                    if (it) _uiState.update { state->
                        state.copy(errorMessages = R.string.Something_went_wrong_cant_update_task.toString())
                    }
                }
            }
            _uiState.update {
                it.copy(task = updatedTask)
            }
        }
    }

    fun onEditClick() {
        _uiState.update {
            it.copy(isEditBottomSheetVisible = true)
        }
    }

}
