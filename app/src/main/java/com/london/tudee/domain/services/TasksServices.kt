package com.london.tudee.domain.services

import com.london.tudee.domain.entities.Category
import com.london.tudee.domain.entities.Task

interface TasksServices {
    fun getAllTasks(): List<Task>
    fun getTaskById(id:Int): Task
    fun createTask(): Task
    fun editTask(id:Int): Task
    fun deleteTask(id:Int): Task

    fun getAllCategory(): List<Category>
    fun getCategoryById(id:Int): Category
    fun createCategory(): Category
    fun editCategory(id:Int): Category
    fun deleteCategory(id:Int): Category

}