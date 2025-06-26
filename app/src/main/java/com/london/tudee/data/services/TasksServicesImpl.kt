package com.london.tudee.data.services

import com.london.tudee.data.local.roomdb.dao.TaskDao
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
        return taskDao.update(service.convertToTaskDto())
    }

    override suspend fun delete(service: Task) {
        return taskDao.delete(service.convertToTaskDto())
    }

    override suspend fun getAll(): Flow<List<Task>> {
        return taskDao.getAll().map { taskDtoList ->
            taskDtoList.map { taskDto -> taskDto.convertToTask() }
        }
    }

    override suspend fun getById(id: Int): Task {
        return taskDao.getById(id).convertToTask()
    }

    override suspend fun getByCategoryId(categoryId: Int): Flow<List<Task>> {
        return taskDao.getByCategoryId(categoryId).map { taskDtoList ->
            taskDtoList.map { taskDto -> taskDto.convertToTask() }
        }
    }

    override suspend fun getByTaskStatus(taskStatus: TaskStatus): Flow<List<Task>> {
        return taskDao.getByTaskStatus(taskStatus).map { taskDtoList ->
            taskDtoList.map { taskDto -> taskDto.convertToTask() }
        }
    }

    override suspend fun getTasksByDate(date: Long): Flow<List<Task>> {
        return taskDao.getTasksByDate(date).map { taskDtoList ->
            taskDtoList.map { taskDto -> taskDto.convertToTask() }
        }
    }

    override suspend fun getByTimeStampAndTaskStatus(
        taskStatus: TaskStatus, timeStamp: Long
    ): Flow<List<Task>> {
        return taskDao.getByTimeStampAndTaskStatus(taskStatus, timeStamp).map { taskDtoList ->
            taskDtoList.map { taskDto -> taskDto.convertToTask() }
        }
    }

    override suspend fun getByCategoryIdAndTaskStatus(
        categoryId: Int, taskStatus: TaskStatus
    ): Flow<List<Task>> {
        return taskDao.getByCategoryIdAndTaskStatus(categoryId, taskStatus).map { taskDtoList ->
            taskDtoList.map { taskDto -> taskDto.convertToTask() }
        }
    }

    override suspend fun getTasksForDay(
        start: Long,
        end: Long,
        taskStatus: TaskStatus
    ): Flow<List<Task>> {
        return taskDao.getTasksForDay(start, end, taskStatus).map { taskDtoList ->
            taskDtoList.map { taskDto -> taskDto.convertToTask() }
        }
    }
}