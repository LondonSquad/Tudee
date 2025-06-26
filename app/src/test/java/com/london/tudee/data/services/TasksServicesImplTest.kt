package com.london.tudee.data.services

import com.google.common.truth.Truth.assertThat
import com.london.tudee.data.exception.TaskException.TaskDeleteException
import com.london.tudee.data.exception.TaskException.TaskInsertException
import com.london.tudee.data.exception.TaskException.TaskLoadException
import com.london.tudee.data.exception.TaskException.TaskNotFoundException
import com.london.tudee.data.exception.TaskException.TaskUpdateException
import com.london.tudee.data.local.roomdb.dao.TaskDao
import com.london.tudee.data.mappers.convertToTaskDto
import com.london.tudee.domain.entities.Priority
import com.london.tudee.domain.entities.Task
import com.london.tudee.domain.entities.TaskStatus
import com.london.tudee.domain.mapper.toMillis
import com.london.tudee.domain.services.TaskService
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Instant
import org.junit.Before
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TasksServicesImplTest {
    private lateinit var taskDao: TaskDao
    private lateinit var taskService: TaskService

    @Before
    fun setUp() {
        taskDao = mockk()
        taskService = TasksServicesImpl(taskDao)
    }

    //region add()
    @Test
    fun `add should call insert in dao and send task to it`() = runTest {
        //given
        coEvery { taskDao.insert(any()) } just Runs
        //when
        taskService.add(task)
        //then
        coVerify(exactly = 1) { taskDao.insert(task.convertToTaskDto()) }
    }

    @Test
    fun `add should throw TaskInsertException when dao throws exception`() = runTest {
        //given
        coEvery { taskDao.insert(any()) } throws Exception()
        //when //then
        assertThrows<TaskInsertException> { taskService.add(task) }
    }
    //endregion

    //region edit()
    @Test
    fun `edit should call update in dao and send task to it`() = runTest {
        //given
        coEvery { taskDao.update(any()) } just Runs
        //when
        taskService.edit(task)
        //then
        coVerify(exactly = 1) { taskDao.update(task.convertToTaskDto()) }
    }

    @Test
    fun `edit should throw TaskUpdateException when dao throws exception`() = runTest {
        //given
        coEvery { taskDao.update(any()) } throws Exception()
        //when //then
        assertThrows<TaskUpdateException> { taskService.edit(task) }
    }
    //endregion

    //region delete()
    @Test
    fun `delete should call delete in dao and send task to it`() = runTest {
        //given
        coEvery { taskDao.delete(any()) } just Runs
        //when
        taskService.delete(task)
        //then
        coVerify(exactly = 1) { taskDao.delete(task.convertToTaskDto()) }
    }

    @Test
    fun `delete should throw TaskDeleteException when dao throws exception`() = runTest {
        //given
        coEvery { taskDao.delete(any()) } throws Exception()
        //when //then
        assertThrows<TaskDeleteException> { taskService.delete(task) }
    }

    //endregion

    //region getAll()
    @Test
    fun `getAll should call getAll in dao and return flow of tasks come from dao`() = runTest {
        // given
        coEvery { taskDao.getAll() } returns flowOf(listOf(task.convertToTaskDto()))
        // when
        val result = taskService.getAll().first()
        // then
        assertThat(result).isEqualTo(listOf(task))
    }

    @Test
    fun `getAll should throw TaskLoadException when dao throws exception`() = runTest {
        // given
        coEvery { taskDao.getAll() } throws Exception()
        // when // then
        assertThrows<TaskLoadException> {
            taskService.getAll()
        }
    }
    //endregion

    //region getById()
    @Test
    fun `getById should call getById in dao and return task come from dao`() = runTest {
        // given
        coEvery { taskDao.getById(any()) } returns task.convertToTaskDto()
        // when
        val result = taskService.getById(task.id)
        // then
        assertThat(result).isEqualTo(task)
    }

    @Test
    fun `getById should throw TaskNotFoundException when dao throws exception`() = runTest {
        // given
        coEvery { taskDao.getById(any()) } throws Exception()
        // when // then
        assertThrows<TaskNotFoundException> {
            taskService.getById(task.id)
        }
    }
    //endregion

    //region getByCategoryId()
    @Test
    fun `getByCategoryId should call getByCategoryId in dao and return flow of come from dao`() =
        runTest {
            // given
            coEvery { taskDao.getByCategoryId(any()) } returns flowOf(listOf(task.convertToTaskDto()))
            // when
            val result = taskService.getByCategoryId(1).first()
            // then
            assertThat(result).isEqualTo(listOf(task))
        }

    @Test
    fun `getByCategoryId should throw TaskLoadException when dao throws exception`() = runTest {
        // given
        coEvery { taskDao.getByCategoryId(any()) } throws Exception()
        // when // then
        assertThrows<TaskLoadException> {
            taskService.getByCategoryId(1)
        }
    }
    //endregion

    //region getByTaskStatus()
    @Test
    fun `getByTaskStatus should call getByTaskStatus in dao and return flow of come from dao`() =
        runTest {
            // given
            coEvery { taskDao.getByTaskStatus(any()) } returns flowOf(listOf(task.convertToTaskDto()))
            // when
            val result = taskService.getByTaskStatus(task.taskStatus).first()
            // then
            assertThat(result).isEqualTo(listOf(task))
        }

    @Test
    fun `getByTaskStatus should throw TaskLoadException when dao throws exception`() = runTest {
        // given
        coEvery { taskDao.getByTaskStatus(any()) } throws Exception()
        // when // then
        assertThrows<TaskLoadException> {
            taskService.getByTaskStatus(task.taskStatus)
        }
    }
    //endregion

    //region getTasksByDate()
    @Test
    fun `getTasksByDate should call getTasksByDate in dao and return flow of come from dao`() =
        runTest {
            // given
            coEvery { taskDao.getTasksByDate(any()) } returns flowOf(listOf(task.convertToTaskDto()))
            // when
            val result = taskService.getTasksByDate(task.timeStamp.toMillis()).first()
            // then
            assertThat(result).isEqualTo(listOf(task))
        }

    @Test
    fun `getTasksByDate should throw TaskLoadException when dao throws exception`() = runTest {
        // given
        coEvery { taskDao.getTasksByDate(any()) } throws Exception()
        // when // then
        assertThrows<TaskLoadException> {
            taskService.getTasksByDate(task.timeStamp.toMillis())
        }
    }
    //endregion

    //region getByTimeStampAndTaskStatus()
    @Test
    fun `getByTimeStampAndTaskStatus should call getByTimeStampAndTaskStatus in dao and return flow of come from dao`() =
        runTest {
            // given
            coEvery {
                taskDao.getByTimeStampAndTaskStatus(
                    any(),
                    any()
                )
            } returns flowOf(listOf(task.convertToTaskDto()))
            // when
            val result = taskService.getByTimeStampAndTaskStatus(
                timeStamp = task.timeStamp.toMillis(),
                taskStatus = task.taskStatus
            ).first()
            // then
            assertThat(result).isEqualTo(listOf(task))
        }

    @Test
    fun `getByTimeStampAndTaskStatus should throw TaskLoadException when dao throws exception`() =
        runTest {
            // given
            coEvery { taskDao.getByTimeStampAndTaskStatus(any(), any()) } throws Exception()
            // when // then
            assertThrows<TaskLoadException> {
                taskService.getByTimeStampAndTaskStatus(
                    timeStamp = task.timeStamp.toMillis(),
                    taskStatus = task.taskStatus
                )
            }
        }
    //endregion

    //region getByCategoryIdAndTaskStatus()
    @Test
    fun `getByCategoryIdAndTaskStatus should call getByCategoryIdAndTaskStatus in dao and return flow of come from dao`() =
        runTest {
            // given
            coEvery { taskDao.getByCategoryIdAndTaskStatus(any(), any()) } returns flowOf(
                listOf(
                    task.convertToTaskDto()
                )
            )
            // when
            val result = taskService.getByCategoryIdAndTaskStatus(
                categoryId = task.categoryId,
                taskStatus = task.taskStatus
            ).first()
            // then
            assertThat(result).isEqualTo(listOf(task))
        }

    @Test
    fun `getByCategoryIdAndTaskStatus should throw TaskLoadException when dao throws exception`() =
        runTest {
            // given
            coEvery { taskDao.getByCategoryIdAndTaskStatus(any(), any()) } throws Exception()
            // when // then
            assertThrows<TaskLoadException> {
                taskService.getByCategoryIdAndTaskStatus(
                    categoryId = task.categoryId,
                    taskStatus = task.taskStatus
                )
            }
        }
    //endregion

    //region getTasksForDay()
    @Test
    fun `getTasksForDay should call getTasksForDay in dao and return flow of come from dao`() =
        runTest {
            // given
            coEvery {
                taskDao.getTasksForDay(
                    any(),
                    any(),
                    any()
                )
            } returns flowOf(listOf(task.convertToTaskDto()))
            // when
            val result = taskService.getTasksForDay(
                task.timeStamp.toMillis(),
                task.timeStamp.toMillis(),
                task.taskStatus
            ).first()
            // then
            assertThat(result).isEqualTo(listOf(task))
        }

    @Test
    fun `getTasksForDay should throw TaskLoadException when dao throws exception`() = runTest {
        // given
        coEvery { taskDao.getTasksForDay(any(), any(), any()) } throws Exception()
        // when // then
        assertThrows<TaskLoadException> {
            taskService.getTasksForDay(
                task.timeStamp.toMillis(),
                task.timeStamp.toMillis(),
                task.taskStatus
            )
        }
    }
    //endregion

    private companion object {
        val task = Task(
            title = "Test",
            description = "Test",
            taskStatus = TaskStatus.IN_PROGRESS,
            priority = Priority.LOW,
            categoryId = 1,
            timeStamp = Instant.parse("2025-06-26T12:30:45.123Z")
        )

    }
}