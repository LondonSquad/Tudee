package com.london.tudee.domain.entities

data class Task(
    val id: Int,
    val title: String,
    val description: String,
    val taskStatus: TaskStatus,
    val priority: Priority,
    val categoryId: Int,
    val timeStamp: Long = System.currentTimeMillis()
)