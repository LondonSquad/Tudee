package com.london.tudee.presentation.base

import com.london.tudee.domain.entities.Category
import com.london.tudee.domain.entities.Priority
import com.london.tudee.domain.entities.Task

interface HomeInteractions {
    fun loadCategories()
    fun updateTitle(title: String)
    fun updateDescription(description: String)
    fun updateSelectedDate(date: Long)
    fun updateSelectedPriority(priority: Priority)
    fun updateSelectedCategory(category: Category)
    fun showDatePicker()
    fun hideDatePicker()
    fun showBottomSheet()
    fun hideBottomSheet()
    fun saveTask()
    fun validateForm()
    fun clearMessages()
    fun loadTask(task: Task)
    fun onClickMove()
    fun showTaskDetailsBottomSheet()
    fun hideTaskDetailsBottomSheet()
    fun initializeForEdit(taskId: Int)
}