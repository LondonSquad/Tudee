package com.london.tudee.data.local.room_db.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.london.tudee.domain.entities.Priority
import com.london.tudee.domain.entities.TaskStatus

@Entity(tableName = "TASK_TABLE")
data class TaskDto(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String,
    val description: String,
    val taskStatus: TaskStatus,
    val priority: Priority,
    val categoryId: Int,
    val timeStamp: Long = System.currentTimeMillis(),
)