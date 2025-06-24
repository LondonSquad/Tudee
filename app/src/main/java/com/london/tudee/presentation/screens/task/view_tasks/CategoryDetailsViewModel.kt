package com.london.tudee.presentation.screens.task.view_tasks

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.london.tudee.domain.entities.Category
import com.london.tudee.domain.entities.TaskStatus
import com.london.tudee.domain.services.CategoryService
import com.london.tudee.domain.services.TaskService
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
) : ViewModel() {

    private val _uiState = MutableStateFlow(CategoryDetailsState())
    val uiState = _uiState.asStateFlow()

    init {
        getDoneTasksByCategoryId(uiState.value.category.id)
        getToDoTasksByCategoryId(uiState.value.category.id)
        getInProgressTasksByCategoryId(uiState.value.category.id)
        Log.d("ID", "ID ${uiState.value.category.id}")
        getCategoryNameById(5) //for testing until nav completed
    }

    private fun getDoneTasksByCategoryId(categoryId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            taskService.getByCategoryIdAndTaskStatus(
                categoryId = categoryId,
                taskStatus = TaskStatus.DONE
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

    private fun getInProgressTasksByCategoryId(categoryId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            taskService.getByCategoryIdAndTaskStatus(
                categoryId = categoryId,
                taskStatus = TaskStatus.IN_PROGRESS
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

    private fun getToDoTasksByCategoryId(categoryId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            taskService.getByCategoryIdAndTaskStatus(
                categoryId = categoryId,
                taskStatus = TaskStatus.TODO
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

    private fun getCategoryNameById(categoryId: Int) {
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
            } catch (e: Exception) {
                Log.e(
                    "CategoryDetailsViewModel",
                    "Error fetching category $categoryId: ${e.message}",
                    e
                )
                // Handle error state
                _uiState.update {
                    it.copy(errMessage = "Category not found")
                }
            }
        }
    }
}