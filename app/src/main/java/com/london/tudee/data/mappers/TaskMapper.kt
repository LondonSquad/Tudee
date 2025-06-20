package com.london.tudee.data.mappers

import com.london.tudee.data.local.room_db.dto.TaskDto
import com.london.tudee.domain.entities.Task
import com.london.tudee.domain.mapper.convertToInstant
import com.london.tudee.domain.mapper.convertToMillis

fun TaskDto.convertToTask(): Task {
    return Task(
        id = this.id,
        title = this.title,
        description = this.description,
        taskStatus = this.taskStatus,
        priority = this.priority,
        categoryId = this.categoryId,
        timeStamp = this.timeStamp.convertToInstant(),
    )
}

fun Task.convertToTaskDto(): TaskDto {
    return TaskDto(
        id = this.id,
        title = this.title,
        description = this.description,
        taskStatus = this.taskStatus,
        priority = this.priority,
        categoryId = this.categoryId,
        timeStamp = this.timeStamp.convertToMillis(),
    )
}