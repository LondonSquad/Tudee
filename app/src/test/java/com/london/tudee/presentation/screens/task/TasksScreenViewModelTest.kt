package com.london.tudee.presentation.screens.task

import com.google.common.truth.Truth.assertThat
import com.london.tudee.R
import com.london.tudee.domain.entities.Category
import com.london.tudee.domain.entities.Task
import com.london.tudee.domain.entities.TaskStatus
import com.london.tudee.domain.entities.Priority
import com.london.tudee.domain.services.CategoryService
import com.london.tudee.domain.services.TaskService
import com.london.tudee.presentation.utils.DateFormatter.toLocalDate
import com.london.tudee.presentation.utils.DateFormatter.toLongDate
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Instant
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TasksScreenViewModelTest {

 private val testDispatcher = StandardTestDispatcher()

 private lateinit var taskService: TaskService
 private lateinit var categoryService: CategoryService
 private lateinit var viewModel: TasksScreenViewModel

 private val fixedTimestamp = Instant.fromEpochMilliseconds(1640995200000L) // 2022-01-01T00:00:00Z

 @Before
 fun setup() {
  Dispatchers.setMain(testDispatcher)
  clearAllMocks()

  taskService = mockk(relaxed = true)
  categoryService = mockk(relaxed = true)
 }

 @After
 fun tearDown() {
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
  timeStamp = fixedTimestamp
 )

 private fun createTestCategory(
  id: Int = 1,
  title: String = "Work",
  iconRes: String = "ic_work",
  taskCount: Int = 5,
  isDefault: Boolean = false
 ) = Category(
  id = id,
  title = title,
  iconRes = iconRes,
  taskCount = taskCount,
  isDefault = isDefault
 )

 private fun createTestTasks() = listOf(
  createTestTask(1, "Test Task 1", "Description 1", TaskStatus.TODO, Priority.HIGH, 1),
  createTestTask(2, "Test Task 2", "Description 2", TaskStatus.DONE, Priority.MEDIUM, 2),
  createTestTask(3, "Test Task 3", "Description 3", TaskStatus.IN_PROGRESS, Priority.LOW, 1)
 )

 private fun createTestCategories() = listOf(
  createTestCategory(1, "Work", "ic_work", 5),
  createTestCategory(2, "Personal", "ic_personal", 3)
 )

 private fun setupMocksWithFreshData() {
  val testTasks = createTestTasks()
  val testCategories = createTestCategories()

  coEvery { taskService.getTasksForDay(any(), any(), TaskStatus.TODO) } returns flowOf(
   testTasks.filter { it.taskStatus == TaskStatus.TODO }
  )
  coEvery { taskService.getTasksForDay(any(), any(), TaskStatus.IN_PROGRESS) } returns flowOf(
   testTasks.filter { it.taskStatus == TaskStatus.IN_PROGRESS }
  )
  coEvery { taskService.getTasksForDay(any(), any(), TaskStatus.DONE) } returns flowOf(
   testTasks.filter { it.taskStatus == TaskStatus.DONE }
  )
  coEvery { categoryService.getAll() } returns flowOf(testCategories)
  coEvery { taskService.getById(any()) } returns testTasks.first()
  coEvery { taskService.delete(any()) } just Runs
  coEvery { taskService.add(any()) } just Runs
  coEvery { categoryService.edit(any()) } just Runs
 }

 private fun createViewModel(): TasksScreenViewModel {
  setupMocksWithFreshData()
  return TasksScreenViewModel(taskService, categoryService)
 }

 @Test
 fun `updateDateByAction with Next action moves to next month`() = runTest {
  // Given
  val currentDate = LocalDate(2024, 1, 15).toLongDate()
  viewModel = createViewModel()
  advanceUntilIdle()

  // When
  viewModel.updateDateByAction(currentDate, ArrowActions.Next)
  advanceUntilIdle()

  // Then
  with(viewModel.uiState.value) {
   val actualDate = date.toLocalDate()

   // Verify we moved to the next month (February)
   assertThat(actualDate.year).isEqualTo(2024)
   assertThat(actualDate.monthNumber).isEqualTo(2)
   assertThat(days).isNotEmpty()

   // Verify the day is within valid range for February
   assertThat(actualDate.dayOfMonth).isIn(1..29)
  }
 }

 @Test
 fun `updateDateByAction with Previous action moves to previous month`() = runTest {
  // Given
  val currentDate = LocalDate(2024, 3, 15).toLongDate()
  viewModel = createViewModel()
  advanceUntilIdle()

  // When
  viewModel.updateDateByAction(currentDate, ArrowActions.Previous)
  advanceUntilIdle()

  // Then
  with(viewModel.uiState.value) {
   val actualDate = date.toLocalDate()

   assertThat(actualDate.year).isEqualTo(2024)
   assertThat(actualDate.monthNumber).isEqualTo(2) // February
   assertThat(days).isNotEmpty()
  }
 }

 @Test
 fun `updateDateByAction with None action keeps same date`() = runTest {
  // Given
  val currentDate = LocalDate(2024, 1, 15).toLongDate()
  viewModel = createViewModel()
  advanceUntilIdle()

  // When
  viewModel.updateDateByAction(currentDate, ArrowActions.None)

  // Then
  with(viewModel.uiState.value) {
   val actualDate = date.toLocalDate()
   val expectedDate = currentDate.toLocalDate()
   assertThat(actualDate.year).isEqualTo(expectedDate.year)
   assertThat(actualDate.monthNumber).isEqualTo(expectedDate.monthNumber)
   assertThat(days).isNotEmpty()
  }
 }

 @Test
 fun `onDaySelected updates selected day correctly`() = runTest {
  // Given
  viewModel = createViewModel()
  advanceUntilIdle()

  // Ensure we have enough days
  val selectedIndex = minOf(10, viewModel.uiState.value.days.size - 1)

  // When
  viewModel.onDaySelected(selectedIndex)
  advanceUntilIdle()

  // Then
  with(viewModel.uiState.value) {
   assertThat(dayItemIndex).isEqualTo(selectedIndex)
   assertThat(days[selectedIndex].isSelected).isTrue()

   // Verify only one day is selected
   val selectedDays = days.count { it.isSelected }
   assertThat(selectedDays).isEqualTo(1)
  }
 }

 @Test
 fun `showDeleteDialog shows dialog with correct task ID`() = runTest {
  // Given
  val taskId = 123
  viewModel = createViewModel()
  advanceUntilIdle()

  // When
  viewModel.showDeleteDialog(taskId)

  // Then
  with(viewModel.uiState.value) {
   assertThat(selectedTaskId).isEqualTo(taskId)
   assertThat(isDeleteDialogVisible).isTrue()
  }
 }

 @Test
 fun `dismissDeleteDialog hides dialog and clears task ID`() = runTest {
  // Given
  viewModel = createViewModel()
  viewModel.showDeleteDialog(123)
  advanceUntilIdle()

  // When
  viewModel.dismissDeleteDialog()

  // Then
  with(viewModel.uiState.value) {
   assertThat(selectedTaskId).isNull()
   assertThat(isDeleteDialogVisible).isFalse()
  }
 }

 @Test
 fun `saveTask creates new task successfully`() = runTest {
  // Given
  val testCategory = createTestCategories().first()
  viewModel = createViewModel()
  advanceUntilIdle()

  // Set up valid form
  viewModel.updateTitle("New Task")
  viewModel.updateDescription("New Description")
  viewModel.updateDate(fixedTimestamp.toEpochMilliseconds())
  viewModel.updateCategory(testCategory)
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
 fun `saveTask does not save when form is invalid`() = runTest {
  // Given
  viewModel = createViewModel()
  advanceUntilIdle()

  // When - saveTask with invalid form (no title, date, category)
  viewModel.saveTask()
  advanceUntilIdle()

  // Then
  coVerify(exactly = 0) { taskService.add(any()) }
  coVerify(exactly = 0) { taskService.edit(any()) }
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
  viewModel.updateDate(testDate)

  // Then
  assertThat(viewModel.taskUiState.value.selectedDate).isEqualTo(testDate)
 }

 @Test
 fun `updatePriority should update selected priority and validate form`() = runTest {
  // Given
  viewModel = createViewModel()
  advanceUntilIdle()

  // When
  viewModel.updatePriority(Priority.HIGH)

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
  viewModel.updateCategory(newCategory)

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
 fun `hideBottomSheet should set showBottomSheet to false`() = runTest {
  // Given
  viewModel = createViewModel()
  viewModel.showBottomSheet()
  advanceUntilIdle()

  // When
  viewModel.hideBottomSheet()

  // Then
  assertThat(viewModel.taskUiState.value.showBottomSheet).isFalse()
 }

 @Test
 fun `validateForm should set isFormValid correctly`() = runTest {
  // Given
  val testCategory = createTestCategories().first()
  viewModel = createViewModel()
  advanceUntilIdle()

  // When - Invalid form (missing title, date)
  viewModel.validateForm()

  // Then
  assertThat(viewModel.taskUiState.value.isFormValid).isFalse()

  // When - Valid form
  viewModel.updateTitle("Valid Title")
  viewModel.updateDate(fixedTimestamp.toEpochMilliseconds())
  viewModel.updateCategory(testCategory)
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
  assertThat(viewModel.taskUiState.value.stateMessage).isNull()
 }

 @Test
 fun `onDateSelected with date picker updates calendar correctly`() = runTest {
  // Given
  val testDate = LocalDate(2024, 6, 15).toLongDate()
  viewModel = createViewModel()
  advanceUntilIdle()

  // When
  viewModel.onDateSelected(testDate)
  advanceUntilIdle()

  // Then
  with(viewModel.uiState.value) {
   val actualDate = date.toLocalDate()
   assertThat(actualDate.year).isEqualTo(2024)
   assertThat(actualDate.monthNumber).isEqualTo(6)
   assertThat(actualDate.dayOfMonth).isEqualTo(15)
   assertThat(dayItemIndex).isEqualTo(14) // 15th day has index 14

   // Verify the selected day is marked as selected
   assertThat(days[dayItemIndex].isSelected).isTrue()
  }
 }

 @Test
 fun `loadCategories handles success correctly`() = runTest {
  // Given
  val testCategories = createTestCategories()
  viewModel = createViewModel()
  advanceUntilIdle()

  // Clear existing mock calls
  clearMocks(categoryService, answers = false)
  coEvery { categoryService.getAll() } returns flowOf(testCategories)

  // When
  viewModel.loadCategories()
  advanceUntilIdle()

  // Then
  with(viewModel.taskUiState.value) {
   assertThat(categories).isEqualTo(testCategories)
   assertThat(categoryIcons).isEqualTo(testCategories.map { it.iconRes })
   assertThat(stateMessage).isNull()
  }

  coVerify { categoryService.getAll() }
 }


 @Test
 fun `deleteTask handles error correctly when task ID is null`() = runTest {
  // Given
  viewModel = createViewModel()
  advanceUntilIdle()

  var successCalled = false
  var errorCalled = false

  // When - deleteTask without setting selectedTaskId
  viewModel.deleteTask(
   onSuccess = { successCalled = true },
   onError = { errorCalled = true }
  )
  advanceUntilIdle()

  // Then
  assertThat(successCalled).isFalse()
  assertThat(errorCalled).isFalse()

  with(viewModel.uiState.value) {
   assertThat(selectedTaskId).isNull()
   assertThat(isDeleteDialogVisible).isFalse()
  }

  // Should not call service methods
  coVerify(exactly = 0) { taskService.getById(any()) }
  coVerify(exactly = 0) { taskService.delete(any()) }
 }

}