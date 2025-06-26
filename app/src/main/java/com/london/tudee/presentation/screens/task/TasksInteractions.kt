package com.london.tudee.presentation.screens.task

import com.london.tudee.domain.entities.Category
import com.london.tudee.domain.entities.Priority

interface TasksInteractions: TaskModifyInteractions {
    fun validateForm()
    fun showDatePicker()
    fun hideDatePicker()
    fun showBottomSheet()
    fun hideBottomSheet()
    fun clearMessages()
    override fun saveTask()
    override fun updateTitle(title: String)
    override fun updateDescription(description: String)
    override fun updateDate(date: Long)
    override fun updatePriority(priority: Priority)
    override fun updateCategory(category: Category)
}