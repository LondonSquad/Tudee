package com.london.tudee.data.services

import com.london.tudee.domain.entities.Category
import com.london.tudee.domain.entities.Priority
import com.london.tudee.domain.entities.Task
import com.london.tudee.domain.entities.TaskStatus
import com.london.tudee.domain.services.TasksServices
import java.util.concurrent.atomic.AtomicInteger

class TaskServicesImpl : TasksServices {

    private val taskIdCounter = AtomicInteger(0)
    private val categoryIdCounter = AtomicInteger(0)

    private val tasks = mutableListOf<Task>()
    private val categories = mutableListOf<Category>()

    override fun getAllTasks(): List<Task> = tasks.toList()

    override fun getTaskById(id: Int): Task {
        return tasks.firstOrNull { it.id == id }
            ?: throw NoSuchElementException("Task with id $id not found")
    }

    override fun createTask(): Task {
        val newTask = Task(
            id = taskIdCounter.incrementAndGet(),
            title = "New Task",
            date = System.currentTimeMillis(),
            description = null,
            priority = Priority.MEDIUM,
            categoryId = categories.firstOrNull()?.id ?: 0,
            taskStatus = TaskStatus.TO_DO
        )
        tasks.add(newTask)
        return newTask
    }

    override fun editTask(id: Int): Task {
        val task = getTaskById(id)
        return task.copy(title = "Updated: ${task.title}")
    }

    override fun deleteTask(id: Int): Task {
        val task = getTaskById(id)
        tasks.remove(task)
        return task
    }

    override fun getAllCategory(): List<Category> = categories.toList()

    override fun getCategoryById(id: Int): Category {
        return categories.firstOrNull { it.id == id }
            ?: throw NoSuchElementException("Category with id $id not found")
    }

    override fun createCategory(): Category {
        val newCategory = Category(
            id = categoryIdCounter.incrementAndGet(),
            title = "New Category",
            imageRes = android.R.drawable.ic_menu_help,
            task = emptyList()
        )
        categories.add(newCategory)
        return newCategory
    }

    override fun editCategory(id: Int): Category {
        val category = getCategoryById(id)
        return category.copy(title = "Updated: ${category.title}")
    }

    override fun deleteCategory(id: Int): Category {
        val category = getCategoryById(id)
        categories.remove(category)
        tasks.removeAll { it.categoryId == id }
        return category
    }

    fun createTask(
        title: String,
        description: String? = null,
        priority: Priority = Priority.MEDIUM,
        categoryId: Int = categories.firstOrNull()?.id ?: 0,
        status: TaskStatus = TaskStatus.TO_DO
    ): Task {
        val newTask = Task(
            id = taskIdCounter.incrementAndGet(),
            title = title,
            date = System.currentTimeMillis(),
            description = description,
            priority = priority,
            categoryId = categoryId,
            taskStatus = status
        )
        tasks.add(newTask)
        return newTask
    }

    fun createCategory(
        title: String,
        imageRes: Int = android.R.drawable.ic_menu_help
    ): Category {
        val newCategory = Category(
            id = categoryIdCounter.incrementAndGet(),
            title = title,
            imageRes = imageRes,
            task = emptyList()
        )
        categories.add(newCategory)
        return newCategory
    }
}