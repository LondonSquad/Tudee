package com.london.tudee.presentation.base

import com.london.tudee.domain.entities.Category
import com.london.tudee.domain.entities.Priority

interface AddOrEditInteractions {
    fun saveTask()
    fun showBottomSheet()
    fun updateTitle(title: String)
    fun updateDescription(description: String)
    fun updateSelectedDate(date: Long)
    fun updateSelectedPriority(priority: Priority)
    fun updateSelectedCategory(category: Category)
    fun showDatePicker()
    fun hideDatePicker()
    fun hideBottomSheet()
}