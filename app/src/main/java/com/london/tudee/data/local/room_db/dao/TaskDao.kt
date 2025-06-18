package com.london.tudee.data.local.room_db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.london.tudee.data.local.room_db.dto.TaskDto
import com.london.tudee.domain.entities.TaskStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM TASK_TABLE")
    fun getAll(): Flow<List<TaskDto>>

    @Query("SELECT * FROM TASK_TABLE WHERE id = :id")
    fun getById(id: Int): TaskDto

    @Query("SELECT * FROM TASK_TABLE WHERE categoryId = :categoryId")
    fun getByCategoryId(categoryId: Int): Flow<List<TaskDto>>

    @Query("SELECT * FROM TASK_TABLE WHERE taskStatus = :taskStatus")
    fun getByTaskStatus(taskStatus: TaskStatus): Flow<List<TaskDto>>

    @Insert
    suspend fun insert(task: TaskDto)

    @Update
    suspend fun update(task: TaskDto)

    @Delete
    suspend fun delete(task: TaskDto)
}