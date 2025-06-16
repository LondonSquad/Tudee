package com.london.tudee.domain.entities

data class Task(
    val id: Int,
    val title: String,
    val date: Long,
    val description: String?,
    val priority: Priority,
    val categoryId: Int,
    val taskStatus: TaskStatus
)

