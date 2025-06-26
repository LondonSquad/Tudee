package com.london.tudee.presentation.screens.task

import com.london.tudee.domain.entities.Category
import com.london.tudee.domain.entities.Priority

interface TaskModifyInteractions {
    fun saveTask()
    fun updateTitle(title: String)
    fun updateDescription(description: String)
    fun updateDate(date: Long)
    fun updatePriority(priority: Priority)
    fun updateCategory(category: Category)
    fun hideBottomSheet()
    fun showDatePicker()
    fun hideDatePicker()
}