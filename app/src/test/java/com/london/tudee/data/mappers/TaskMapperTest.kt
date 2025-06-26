package com.london.tudee.data.mappers

import com.london.tudee.data.local.roomdb.dto.TaskDto
import com.london.tudee.domain.entities.Priority
import com.london.tudee.domain.entities.Task
import com.london.tudee.domain.entities.TaskStatus
import  kotlin.test.assertEquals
import kotlinx.datetime.Instant
import org.junit.jupiter.api.Test

class TaskMapperTest {
    @Test
    fun `when convert TaskDto to convertToTask returns Task`() {
        //Given & When
        val result = testTaskDto().convertToTask()
        //Then
        assertEquals(result, testTask())
    }

    @Test
    fun `when convert Task to convertToTaskDto returns TaskDto`() {
        //Given & When
        val result = testTask().convertToTaskDto()
        //Then
        assertEquals(result, testTaskDto())
    }

    private fun testTask() = Task(
        id = 1,
        title = "Test Task",
        description = "Test Description",
        taskStatus = TaskStatus.TODO,
        priority = Priority.LOW,
        categoryId = 1,
        timeStamp = Instant.fromEpochMilliseconds(0)
    )

    private fun testTaskDto() = TaskDto(
        id = 1,
        title = "Test Task",
        description = "Test Description",
        status = TaskStatus.TODO,
        priority = Priority.LOW,
        categoryId = 1,
        timeStamp = 0
    )
}