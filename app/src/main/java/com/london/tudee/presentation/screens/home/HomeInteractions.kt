package com.london.tudee.presentation.screens.home

import com.london.tudee.domain.entities.Category
import com.london.tudee.domain.entities.Priority
import com.london.tudee.domain.entities.Task
import com.london.tudee.presentation.screens.task.TaskModifyInteractions

interface HomeInteractions : TaskModifyInteractions {
    fun showDatePicker()
    fun hideDatePicker()
    fun showBottomSheet()
    fun hideBottomSheet()
    fun onThemeSwitched(isDarkMode: Boolean)
    fun loadCategories()
    fun validateForm()
    fun clearMessages()
    fun loadTask(task: Task)
    fun onClickMove()
    fun showTaskDetailsBottomSheet()
    fun hideTaskDetailsBottomSheet()
    fun onEditTask(taskId: Int)
    override fun updateTitle(title: String)
    override fun updateDescription(description: String)
    override fun updateDate(date: Long)
    override fun updatePriority(priority: Priority)
    override fun updateCategory(category: Category)
    override fun saveTask()
}