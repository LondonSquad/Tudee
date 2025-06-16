package com.london.tudee.data.services

import com.london.tudee.domain.entities.Category
import com.london.tudee.domain.entities.Task
import com.london.tudee.domain.services.TasksServices

class FakeDataImpl (

): TasksServices {
    override fun getAllTasks(): List<Task> {
        TODO("Not yet implemented")
    }

    override fun getTaskById(id: Int): Task {
        TODO("Not yet implemented")
    }

    override fun createTask(): Task {
        TODO("Not yet implemented")
    }

    override fun editTask(id: Int): Task {
        TODO("Not yet implemented")
    }

    override fun deleteTask(id: Int): Task {
        TODO("Not yet implemented")
    }

    override fun getAllCategory(): List<Category> {
        TODO("Not yet implemented")
    }

    override fun getCategoryById(id: Int): Category {
        TODO("Not yet implemented")
    }

    override fun createCategory(): Category {
        TODO("Not yet implemented")
    }

    override fun editCategory(id: Int): Category {
        TODO("Not yet implemented")
    }

    override fun deleteCategory(id: Int): Category {
        TODO("Not yet implemented")
    }

}