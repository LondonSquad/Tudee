package com.london.tudee.presentation.screens.home

import androidx.compose.runtime.mutableStateOf
import com.google.common.truth.Truth.assertThat
import com.london.tudee.R
import com.london.tudee.domain.entities.Category
import com.london.tudee.domain.entities.Priority
import com.london.tudee.domain.entities.Task
import com.london.tudee.domain.entities.TaskStatus
import com.london.tudee.domain.services.AppPreferencesService
import com.london.tudee.domain.services.CategoryService
import com.london.tudee.domain.services.TaskService
import io.mockk.Runs
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.datetime.Clock
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var taskService: TaskService
    private lateinit var categoryService: CategoryService
    private lateinit var appPreferences: AppPreferencesService
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        clearAllMocks()

        taskService = mockk(relaxed = true)
        categoryService = mockk(relaxed = true)
        appPreferences = mockk(relaxed = true)
    }

    @After
    fun tearDown() {
        if (::viewModel.isInitialized) {
            viewModel.clearMessages()
        }
        Dispatchers.resetMain()
        clearAllMocks()
    }

    private fun createTestTask(
        id: Int = 1,
        title: String = "Test Task",
        description: String = "Test Description",
        status: TaskStatus = TaskStatus.TODO,
        priority: Priority = Priority.HIGH,
        categoryId: Int = 1
    ) = Task(
        id = id,
        title = title,
        description = description,
        taskStatus = status,
        priority = priority,
        categoryId = categoryId,
        timeStamp = Clock.System.now()
    )

    private fun createTestCategory(
        id: Int = 1,
        title: String = "Work",
        iconRes: String = "work_icon",
        taskCount: Int = 5,
        isDefault: Boolean = true
    ) = Category(
        id = id,
        title = title,
        iconRes = iconRes,
        taskCount = taskCount,
        isDefault = isDefault
    )

    private fun createTestTasks() = listOf(
        createTestTask(1, status = TaskStatus.TODO),
        createTestTask(2, status = TaskStatus.IN_PROGRESS),
        createTestTask(3, status = TaskStatus.DONE)
    )

    private fun createTestCategories() = listOf(createTestCategory())

    private fun setupMocksWithFreshData() {
        val testTasks = createTestTasks()
        val testCategories = createTestCategories()
        val testCategory = createTestCategory()
        val testTask = createTestTask()

        coEvery { taskService.getAll() } returns flowOf(testTasks)
        coEvery { taskService.getByTaskStatus(TaskStatus.TODO) } returns flowOf(
            testTasks.filter { it.taskStatus == TaskStatus.TODO }
        )
        coEvery { taskService.getByTaskStatus(TaskStatus.IN_PROGRESS) } returns flowOf(
            testTasks.filter { it.taskStatus == TaskStatus.IN_PROGRESS }
        )
        coEvery { taskService.getByTaskStatus(TaskStatus.DONE) } returns flowOf(
            testTasks.filter { it.taskStatus == TaskStatus.DONE }
        )
        coEvery { categoryService.getAll() } returns flowOf(testCategories)
        every { categoryService.getIconResById(any()) } returns "test_icon"
        coEvery { categoryService.getById(any()) } returns testCategory
        every { appPreferences.isDarkModeEnabled } returns mutableStateOf(false)
        coEvery { appPreferences.setDarkModeEnabled(any()) } just Runs
        coEvery { taskService.add(any()) } just Runs
        coEvery { taskService.edit(any()) } just Runs
        coEvery { categoryService.edit(any()) } just Runs
        coEvery { taskService.getById(any()) } returns testTask
    }

    private fun createViewModel(): HomeViewModel {
        setupMocksWithFreshData()
        return HomeViewModel(taskService, categoryService, appPreferences)
    }

    @Test
    fun `loadTask should update task detail bottom sheet state`() = runTest {
        // Given
        val testTask = createTestTask()
        viewModel = createViewModel()
        advanceUntilIdle()

        // When
        viewModel.loadTask(testTask)
        advanceUntilIdle()

        // Then
        assertThat(testTask.title).isNotEmpty()
        verify { categoryService.getIconResById(testTask.categoryId) }
    }

    @Test
    fun `onClickMove should move task from TODO to IN_PROGRESS`() = runTest {
        // Given
        val testTask = createTestTask(status = TaskStatus.TODO)
        viewModel = createViewModel()
        advanceUntilIdle()

        // Set up task detail state first
        viewModel.loadTask(testTask)
        advanceUntilIdle()

        // When
        viewModel.onClickMove()
        advanceUntilIdle()

        // Then
        val expectedUpdatedTask = testTask.copy(taskStatus = TaskStatus.IN_PROGRESS)
        coVerify { taskService.edit(expectedUpdatedTask) }

        with(viewModel.uiState.value.taskDetailBottomSheetUiState) {
            assertThat(task.taskStatus).isEqualTo(TaskStatus.IN_PROGRESS)
        }
    }

    @Test
    fun `onClickMove should move task from IN_PROGRESS to DONE`() = runTest {
        // Given
        val inProgressTask = createTestTask(status = TaskStatus.IN_PROGRESS)
        viewModel = createViewModel()
        advanceUntilIdle()

        viewModel.loadTask(inProgressTask)
        advanceUntilIdle()

        // When
        viewModel.onClickMove()
        advanceUntilIdle()

        // Then
        val expectedUpdatedTask = inProgressTask.copy(taskStatus = TaskStatus.DONE)
        coVerify { taskService.edit(expectedUpdatedTask) }

        assertThat(viewModel.uiState.value.taskDetailBottomSheetUiState.task.taskStatus)
            .isEqualTo(TaskStatus.DONE)
    }

    @Test
    fun `showTaskDetailsBottomSheet should set visibility to true`() = runTest {
        // Given
        viewModel = createViewModel()
        advanceUntilIdle()

        // When
        viewModel.showTaskDetailsBottomSheet()

        // Then
        assertThat(viewModel.uiState.value.isTaskDetailsBottomSheetVisible).isTrue()
    }

    @Test
    fun `hideTaskDetailsBottomSheet should set visibility to false`() = runTest {
        // Given
        viewModel = createViewModel()
        viewModel.showTaskDetailsBottomSheet()
        advanceUntilIdle()

        // When
        viewModel.hideTaskDetailsBottomSheet()

        // Then
        assertThat(viewModel.uiState.value.isTaskDetailsBottomSheetVisible).isFalse()
    }

    @Test
    fun `onThemeSwitched should update dark mode preference and state`() = runTest {
        // Given
        viewModel = createViewModel()
        advanceUntilIdle()

        // When
        viewModel.onThemeSwitched(true)
        advanceUntilIdle()

        // Then
        assertThat(viewModel.uiState.value.isDarkMode).isTrue()
        coVerify { appPreferences.setDarkModeEnabled(true) }
    }


    @Test
    fun `updateTitle should update title and validate form`() = runTest {
        // Given
        viewModel = createViewModel()
        advanceUntilIdle()

        // When
        viewModel.updateTitle("New Title")

        // Then
        assertThat(viewModel.taskUiState.value.title).isEqualTo("New Title")
    }

    @Test
    fun `updateDescription should update description`() = runTest {
        // Given
        viewModel = createViewModel()
        advanceUntilIdle()

        // When
        viewModel.updateDescription("New Description")

        // Then
        assertThat(viewModel.taskUiState.value.description).isEqualTo("New Description")
    }

    @Test
    fun `updateDate should update selected date and validate form`() = runTest {
        // Given
        viewModel = createViewModel()
        val testDate = 1640995200000L
        advanceUntilIdle()

        // When
        viewModel.updateSelectedDate(testDate)

        // Then
        assertThat(viewModel.taskUiState.value.selectedDate).isEqualTo(testDate)
    }

    @Test
    fun `updatePriority should update selected priority and validate form`() = runTest {
        // Given
        viewModel = createViewModel()
        advanceUntilIdle()

        // When
        viewModel.updateSelectedPriority(Priority.HIGH)

        // Then
        assertThat(viewModel.taskUiState.value.selectedPriority).isEqualTo(Priority.HIGH)
    }

    @Test
    fun `updateCategory should update selected category and validate form`() = runTest {
        // Given
        viewModel = createViewModel()
        val newCategory = createTestCategory(id = 2, title = "Personal")
        advanceUntilIdle()

        // When
        viewModel.updateSelectedCategory(newCategory)

        // Then
        assertThat(viewModel.taskUiState.value.selectedCategory).isEqualTo(newCategory)
    }

    @Test
    fun `showDatePicker should set showDatePicker to true`() = runTest {
        // Given
        viewModel = createViewModel()
        advanceUntilIdle()

        // When
        viewModel.showDatePicker()

        // Then
        assertThat(viewModel.taskUiState.value.showDatePicker).isTrue()
    }

    @Test
    fun `hideDatePicker should set showDatePicker to false`() = runTest {
        // Given
        viewModel = createViewModel()
        viewModel.showDatePicker()
        advanceUntilIdle()

        // When
        viewModel.hideDatePicker()

        // Then
        assertThat(viewModel.taskUiState.value.showDatePicker).isFalse()
    }

    @Test
    fun `showBottomSheet should set showBottomSheet to true`() = runTest {
        // Given
        viewModel = createViewModel()
        advanceUntilIdle()

        // When
        viewModel.showBottomSheet()

        // Then
        assertThat(viewModel.taskUiState.value.showBottomSheet).isTrue()
    }

    @Test
    fun `hideBottomSheet should reset form state after delay`() = runTest {
        // Given
        val testCategory = createTestCategory()
        viewModel = createViewModel()
        viewModel.updateTitle("Test Title")
        viewModel.updateDescription("Test Description")
        viewModel.showBottomSheet()
        advanceUntilIdle()

        // When
        viewModel.hideBottomSheet()
        advanceUntilIdle()

        // Then
        with(viewModel.taskUiState.value) {
            assertThat(showBottomSheet).isFalse()
            assertThat(title).isEmpty()
            assertThat(description).isEmpty()
            assertThat(selectedDate).isNull()
            assertThat(selectedPriority).isEqualTo(Priority.LOW)
            assertThat(selectedCategory).isEqualTo(testCategory)
            assertThat(stateMessage).isNull()
            assertThat(isEditMode).isFalse()
            assertThat(taskId).isNull()
        }
    }

    @Test
    fun `saveTask should create new task when not in edit mode`() = runTest {
        // Given
        val testCategory = createTestCategory()
        viewModel = createViewModel()
        advanceUntilIdle()

        // Set up valid form
        viewModel.updateTitle("New Task")
        viewModel.updateDescription("New Description")
        viewModel.updateSelectedDate(Clock.System.now().toEpochMilliseconds())
        viewModel.updateSelectedCategory(testCategory)
        advanceUntilIdle()

        // When
        viewModel.saveTask()
        advanceUntilIdle()

        // Then
        coVerify { taskService.add(any()) }
        coVerify { categoryService.edit(any()) }

        with(viewModel.taskUiState.value) {
            assertThat(isLoading).isFalse()
            assertThat(stateMessage).isEqualTo(R.string.add_task_successfully)
            assertThat(showBottomSheet).isFalse()
        }
    }


    @Test
    fun `saveTask should not save when form is invalid`() = runTest {
        // Given
        viewModel = createViewModel()
        advanceUntilIdle()

        // When
        viewModel.saveTask()
        advanceUntilIdle()

        // Then
        coVerify(exactly = 0) { taskService.add(any()) }
        coVerify(exactly = 0) { taskService.edit(any()) }
    }

    @Test
    fun `validateForm should set isFormValid correctly`() = runTest {
        // Given
        val testCategory = createTestCategory()
        viewModel = createViewModel()
        advanceUntilIdle()

        // When - Invalid form (missing title, date)
        viewModel.validateForm()

        // Then
        assertThat(viewModel.taskUiState.value.isFormValid).isFalse()

        // When - Valid form
        viewModel.updateTitle("Valid Title")
        viewModel.updateSelectedDate(Clock.System.now().toEpochMilliseconds())
        viewModel.updateSelectedCategory(testCategory)
        advanceUntilIdle()

        // Then
        assertThat(viewModel.taskUiState.value.isFormValid).isTrue()
    }

    @Test
    fun `clearMessages should set stateMessage to null`() = runTest {
        // Given
        viewModel = createViewModel()
        advanceUntilIdle()

        // When
        viewModel.clearMessages()

        // Then
        assertThat(viewModel.taskUiState.value.stateMessage).isEqualTo(null)
    }
}