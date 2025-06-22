package com.london.tudee.presentation.screens.task.add_edit_task_bottom_sheet

import com.london.tudee.domain.entities.Category
import com.london.tudee.domain.entities.Priority

interface AddOrEditInteraction {
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
    fun clearMessages()
    fun initializeForEdit(taskId: Int)
}