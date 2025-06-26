package com.london.tudee.data.local.roomdb.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.london.tudee.data.local.roomdb.dto.TaskDto
import com.london.tudee.domain.entities.TaskStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Insert
    suspend fun insert(task: TaskDto)

    @Update
    suspend fun update(task: TaskDto)

    @Delete
    suspend fun delete(task: TaskDto)

    @Query("SELECT * FROM TASK_TABLE")
    fun getAll(): Flow<List<TaskDto>>

    @Query("SELECT * FROM TASK_TABLE WHERE id = :id")
    fun getById(id: Int): TaskDto

    @Query("SELECT * FROM TASK_TABLE WHERE categoryId = :categoryId")
    fun getByCategoryId(categoryId: Int): Flow<List<TaskDto>>

    @Query("SELECT * FROM TASK_TABLE WHERE status = :taskStatus")
    fun getByTaskStatus(taskStatus: TaskStatus): Flow<List<TaskDto>>

    @Query("SELECT * FROM TASK_TABLE WHERE timeStamp = :time")
    fun getTasksByDate(time: Long): Flow<List<TaskDto>>

    @Query("SELECT * FROM TASK_TABLE WHERE status = :taskStatus AND timeStamp = :timeStamp")
    fun getByTimeStampAndTaskStatus(taskStatus: TaskStatus, timeStamp: Long): Flow<List<TaskDto>>

    @Query("SELECT * FROM TASK_TABLE WHERE categoryid = :categoryId AND status = :taskStatus")
    fun getByCategoryIdAndTaskStatus(categoryId: Int, taskStatus: TaskStatus): Flow<List<TaskDto>>

    @Query("SELECT * FROM TASK_TABLE WHERE timeStamp BETWEEN :start AND :end AND status = :taskStatus")
    fun getTasksForDay(start: Long, end: Long, taskStatus: TaskStatus): Flow<List<TaskDto>>
}