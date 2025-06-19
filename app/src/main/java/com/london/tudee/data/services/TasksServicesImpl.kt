package com.london.tudee.data.services

import com.london.tudee.data.local.room_db.dao.TaskDao
import com.london.tudee.data.mappers.convertToTask
import com.london.tudee.data.mappers.convertToTaskDto
import com.london.tudee.domain.entities.Task
import com.london.tudee.domain.entities.TaskStatus
import com.london.tudee.domain.services.TaskService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TasksServicesImpl(
    private val taskDao: TaskDao
) : TaskService {
    override suspend fun add(service: Task) {
        val task = service.copy(id = 0)
        return taskDao.insert(task.convertToTaskDto())
    }

    override suspend fun edit(service: Task) {
        val task = service.copy(id = 0)
        return taskDao.update(task.convertToTaskDto())
    }

    override suspend fun delete(service: Task) {
        return taskDao.delete(service.convertToTaskDto())
    }

    override suspend fun getAll(): Flow<List<Task>> {
        return taskDao.getAll().map { list ->
            list.map { it.convertToTask() }
        }
    }

    override suspend fun getById(id: Int): Task {
        return taskDao.getById(id).convertToTask()
    }

    override suspend fun getByCategoryId(categoryId: Int): Flow<List<Task>> {
        return taskDao.getByCategoryId(categoryId).map { list ->
            list.map { it.convertToTask() }
        }
    }

    override suspend fun getByTaskStatus(taskStatus: TaskStatus): Flow<List<Task>> {
        return taskDao.getByTaskStatus(taskStatus).map { list ->
            list.map { it.convertToTask() }
        }
    }
}