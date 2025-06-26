package com.london.tudee.presentation.screens.task

import com.london.tudee.domain.entities.Category
import com.london.tudee.domain.entities.Priority

interface TaskModifyInteractions {
    fun saveTask()
    fun showDatePicker()
    fun hideDatePicker()
    fun hideBottomSheet()
    fun updateDate(date: Long)
    fun updateTitle(title: String)
    fun updatePriority(priority: Priority)
    fun updateCategory(category: Category)
    fun updateDescription(description: String)
}