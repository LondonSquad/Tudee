package com.london.tudee.domain.services

import com.london.tudee.domain.entities.Task
import com.london.tudee.domain.entities.TaskStatus
import kotlinx.coroutines.flow.Flow

interface TaskService : Services<Task> {
    suspend fun getByCategoryId(categoryId: Int): Flow<List<Task>>
    suspend fun getByTaskStatus(taskStatus: TaskStatus): Flow<List<Task>>
}