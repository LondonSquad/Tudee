package com.london.tudee.presentation.base

import com.london.tudee.domain.entities.Category
import com.london.tudee.domain.entities.Priority
import com.london.tudee.domain.entities.Task

interface HomeInteractions:AddOrEditInteractions {
    fun onThemeSwitched(isDarkMode: Boolean)
    fun loadCategories()
    override fun updateTitle(title: String)
    override fun updateDescription(description: String)
    override fun updateSelectedDate(date: Long)
    override fun updateSelectedPriority(priority: Priority)
    override fun updateSelectedCategory(category: Category)
    override fun showDatePicker()
    override fun hideDatePicker()
    override fun showBottomSheet()
    override fun hideBottomSheet()
    override fun saveTask()
    fun validateForm()
    fun clearMessages()
    fun loadTask(task: Task)
    fun onClickMove()
    fun showTaskDetailsBottomSheet()
    fun hideTaskDetailsBottomSheet()
    fun initializeForEdit(taskId: Int)
}