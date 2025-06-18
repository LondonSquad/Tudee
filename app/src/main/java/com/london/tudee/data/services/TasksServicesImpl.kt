package com.london.tudee.data.services

import com.london.tudee.data.local.room_db.TudeeDatabase
import com.london.tudee.data.mappers.convertToTask
import com.london.tudee.data.mappers.convertToTaskDto
import com.london.tudee.domain.entities.Task
import com.london.tudee.domain.entities.TaskStatus
import com.london.tudee.domain.services.Services
import com.london.tudee.domain.services.TaskService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TasksServicesImpl(
    private val db: TudeeDatabase
) : TaskService{
    override suspend fun add(service: Task): Boolean {
        return db.taskDao().insert(service.convertToTaskDto()) != -1L
    }

    override suspend fun edit(service: Task): Boolean {
        return db.taskDao().update(service.convertToTaskDto()) != -1
    }

    override suspend fun delete(service: Task): Boolean {
        return db.taskDao().delete(service.convertToTaskDto()) != -1
    }

    override suspend fun getAll(): Flow<List<Task>> {
        return db.taskDao().getAll().map { list ->
            list.map { it.convertToTask() }
        }
    }

    override suspend fun getById(id: Int): Task {
        return db.taskDao().getById(id).convertToTask()
    }

    override suspend fun getByCategoryId(categoryId: Int): Flow<List<Task>> {
        return db.taskDao().getByCategoryId(categoryId).map { list ->
            list.map { it.convertToTask() }
        }
    }

    override suspend fun getByTaskStatus(taskStatus: TaskStatus): Flow<List<Task>> {
        return db.taskDao().getByTaskStatus(taskStatus).map { list ->
            list.map { it.convertToTask() }
        }
    }
}