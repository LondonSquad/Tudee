package com.london.tudee.presentation.screens.home

import com.london.tudee.domain.entities.Category
import com.london.tudee.domain.entities.Priority
import com.london.tudee.domain.entities.Task
import com.london.tudee.presentation.screens.task.TaskModifyInteractions

interface HomeInteractions : TaskModifyInteractions {
    fun showBottomSheet()
    fun loadCategories()
    fun validateForm()
    fun clearMessages()
    fun onClickMove()
    fun showTaskDetailsBottomSheet()
    fun hideTaskDetailsBottomSheet()
    fun loadTask(task: Task)
    fun onEditTask(taskId: Int)
    fun onThemeSwitched(isDarkMode: Boolean)
    override fun saveTask()
    override fun showDatePicker()
    override fun hideDatePicker()
    override fun hideBottomSheet()
    override fun updateDate(date: Long)
    override fun updateTitle(title: String)
    override fun updatePriority(priority: Priority)
    override fun updateCategory(category: Category)
    override fun updateDescription(description: String)
}