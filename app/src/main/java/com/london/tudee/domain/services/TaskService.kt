package com.london.tudee.domain.services

import com.london.tudee.domain.entities.Task
import com.london.tudee.domain.entities.TaskStatus
import kotlinx.coroutines.flow.Flow

interface TaskService : Services<Task> {
    suspend fun getByCategoryId(categoryId: Int): Flow<List<Task>>
    suspend fun getByTaskStatus(status: TaskStatus): Flow<List<Task>>
    suspend fun getTasksByDate(date: Long): Flow<List<Task>>
    suspend fun getByTimeStampAndTaskStatus(
        status: TaskStatus,
        timeStamp: Long
    ): Flow<List<Task>>

    suspend fun getByCategoryIdAndTaskStatus(
        categoryId: Int,
        status: TaskStatus
    ): Flow<List<Task>>

    suspend fun getTasksForDay(start: Long, end: Long, status: TaskStatus): Flow<List<Task>>
}