package com.london.tudee.data.services

import com.london.tudee.data.exception.TaskException.*
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
        runCatching {
            taskDao.insert(service.copy(id = 0).convertToTaskDto())
        }.onFailure {
            throw TaskInsertException()
        }
    }

    override suspend fun edit(service: Task) {
        runCatching {
            taskDao.update(service.convertToTaskDto())
        }.onFailure {
            throw TaskUpdateException()
        }
    }

    override suspend fun delete(service: Task) {
        runCatching {
            taskDao.delete(service.convertToTaskDto())
        }.onFailure {
            throw TaskDeleteException()
        }
    }

    override suspend fun getAll(): Flow<List<Task>> {
        return runCatching {
            taskDao.getAll().map { it.map { dto -> dto.convertToTask() } }
        }.getOrElse {
            throw TaskLoadException()
        }
    }

    override suspend fun getById(id: Int): Task {
        return runCatching {
            taskDao.getById(id).convertToTask()
        }.getOrElse {
            throw TaskNotFoundException()
        }
    }

    override suspend fun getByCategoryId(categoryId: Int): Flow<List<Task>> {
        return runCatching {
            taskDao.getByCategoryId(categoryId).map { it.map { dto -> dto.convertToTask() } }
        }.getOrElse {
            throw TaskLoadException()
        }
    }

    override suspend fun getByTaskStatus(taskStatus: TaskStatus): Flow<List<Task>> {
        return runCatching {
            taskDao.getByTaskStatus(taskStatus).map { it.map { dto -> dto.convertToTask() } }
        }.getOrElse {
            throw TaskLoadException()
        }
    }

    override suspend fun getTasksByDate(date: Long): Flow<List<Task>> {
        return runCatching {
            taskDao.getTasksByDate(date).map { it.map { dto -> dto.convertToTask() } }
        }.getOrElse {
            throw TaskLoadException()
        }
    }

    override suspend fun getByTimeStampAndTaskStatus(
        taskStatus: TaskStatus, timeStamp: Long
    ): Flow<List<Task>> {
        return runCatching {
            taskDao.getByTimeStampAndTaskStatus(taskStatus, timeStamp)
                .map { it.map { dto -> dto.convertToTask() } }
        }.getOrElse {
            throw TaskLoadException()
        }
    }

    override suspend fun getByCategoryIdAndTaskStatus(
        categoryId: Int, taskStatus: TaskStatus
    ): Flow<List<Task>> {
        return runCatching {
            taskDao.getByCategoryIdAndTaskStatus(categoryId, taskStatus)
                .map { it.map { dto -> dto.convertToTask() } }
        }.getOrElse {
            throw TaskLoadException()
        }
    }

    override suspend fun getTasksForDay(
        start: Long, end: Long, taskStatus: TaskStatus
    ): Flow<List<Task>> {
        return runCatching {
            taskDao.getTasksForDay(start, end, taskStatus)
                .map { it.map { dto -> dto.convertToTask() } }
        }.getOrElse {
            throw TaskLoadException()
        }
    }
}
