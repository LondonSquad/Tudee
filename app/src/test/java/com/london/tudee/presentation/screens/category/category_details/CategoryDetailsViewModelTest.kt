package com.london.tudee.presentation.screens.category.category_details

import com.london.tudee.R
import com.london.tudee.domain.entities.Category
import com.london.tudee.domain.entities.Priority
import com.london.tudee.domain.entities.Task
import com.london.tudee.domain.entities.TaskStatus
import com.london.tudee.domain.services.CategoryService
import com.london.tudee.domain.services.TaskService
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

@OptIn(ExperimentalCoroutinesApi::class)
class CategoryDetailsViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var taskService: TaskService
    private lateinit var categoryService: CategoryService
    private lateinit var viewModel: CategoryDetailsViewModel

    private val testCategory =
        Category(id = 10, title = "Work", iconRes = "icon", isDefault = false, taskCount = 5)

    private val testTasks = listOf(
        Task(id = 1, title = "Task 1", description = "Description 1", taskStatus = TaskStatus.TODO, priority = Priority.MEDIUM, categoryId = 10),
        Task(id = 2, title = "Task 2", description = "Description 2", taskStatus = TaskStatus.IN_PROGRESS, priority = Priority.HIGH, categoryId = 10),
        Task(id = 3, title = "Task 3", description = "Description 3", taskStatus = TaskStatus.DONE, priority = Priority.LOW, categoryId = 10)
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        taskService = mockk(relaxed = true)
        categoryService = mockk(relaxed = true)
        viewModel = CategoryDetailsViewModel(taskService, categoryService)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initializeWithCategoryId should call all initialization methods`() = runTest {
        // Given
        val categoryId = 10
        coEvery { taskService.getByCategoryIdAndTaskStatus(any(), any()) } returns flowOf(emptyList())
        coEvery { categoryService.getById(any()) } returns testCategory

        // When
        viewModel.initializeWithCategoryId(categoryId)
        advanceUntilIdle()

        // Then
        coVerify { taskService.getByCategoryIdAndTaskStatus(categoryId, TaskStatus.DONE) }
        coVerify { taskService.getByCategoryIdAndTaskStatus(categoryId, TaskStatus.TODO) }
        coVerify { taskService.getByCategoryIdAndTaskStatus(categoryId, TaskStatus.IN_PROGRESS) }
        coVerify { categoryService.getById(categoryId) }
    }

    @Test
    fun `editCategory should update loading state and call service`() = runTest {
        // Given
        coEvery { categoryService.edit(testCategory) } just Runs

        // When
        viewModel.editCategory(testCategory)
        advanceUntilIdle()

        // Then
        coVerify { categoryService.edit(testCategory) }
        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
    }

    @Test
    fun `editCategory should handle error`() = runTest {
        // Given
        val errorMessage = "Edit failed"
        coEvery { categoryService.edit(testCategory) } throws Exception(errorMessage)

        // When
        viewModel.editCategory(testCategory)
        advanceUntilIdle()

        // Then
        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertEquals(errorMessage, state.errorMessage)
    }

    @Test
    fun `deleteCategory should update state and call service`() = runTest {
        // Given
        coEvery { categoryService.delete(testCategory) } just Runs

        // When
        viewModel.deleteCategory(testCategory)
        advanceUntilIdle()

        // Then
        coVerify { categoryService.delete(testCategory) }
        val state = viewModel.uiState.value
        assertTrue(state.isDeleted)
        assertFalse(state.isLoading)
    }

    @Test
    fun `deleteCategory should handle error`() = runTest {
        // Given
        val errorMessage = "Delete failed"
        coEvery { categoryService.delete(testCategory) } throws Exception(errorMessage)

        // When
        viewModel.deleteCategory(testCategory)
        advanceUntilIdle()

        // Then
        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertEquals(errorMessage, state.errorMessage)
    }

    @Test
    fun `onCategoryDeleted should update state with success message`() {
        // When
        viewModel.onCategoryDeleted()

        // Then
        val state = viewModel.uiState.value
        assertTrue(state.categoryDeleted)
        assertEquals(R.string.delete_category_successfully, state.stateMessage)
        assertTrue(state.showSuccessMessage)
    }

    @Test
    fun `onCategoryEditError should update state with error message`() {
        // When
        viewModel.onCategoryEditError()

        // Then
        val state = viewModel.uiState.value
        assertEquals(R.string.some_error_happened, state.stateMessage)
        assertTrue(state.showErrorMessage)
    }

    @Test
    fun `onCategoryDeleteError should update state with error message`() {
        // When
        viewModel.onCategoryDeleteError()

        // Then
        val state = viewModel.uiState.value
        assertEquals(R.string.some_error_happened, state.stateMessage)
        assertTrue(state.showErrorMessage)
    }

    @Test
    fun `clearMessages should reset all message states`() {
        // Given
        viewModel.onCategoryDeleted()

        // When
        viewModel.clearMessages()

        // Then
        val state = viewModel.uiState.value
        assertNull(state.stateMessage)
        assertFalse(state.showSuccessMessage)
        assertFalse(state.showErrorMessage)
    }

    @Test
    fun `showEditBottomSheet should set edit bottom sheet visible`() {
        // When
        viewModel.showEditBottomSheet()

        // Then
        assertTrue(viewModel.uiState.value.isEditBottomSheetVisible)
    }

    @Test
    fun `hideEditBottomSheet should set edit bottom sheet invisible`() {
        // Given
        viewModel.showEditBottomSheet()

        // When
        viewModel.hideEditBottomSheet()

        // Then
        assertFalse(viewModel.uiState.value.isEditBottomSheetVisible)
    }

    @Test
    fun `showDeleteBottomSheet should set delete bottom sheet visible`() {
        // When
        viewModel.showDeleteBottomSheet()

        // Then
        assertTrue(viewModel.uiState.value.isDeleteBottomSheetVisible)
    }

    @Test
    fun `hideDeleteBottomSheet should set delete bottom sheet invisible`() {
        // Given
        viewModel.showDeleteBottomSheet()

        // When
        viewModel.hideDeleteBottomSheet()

        // Then
        assertFalse(viewModel.uiState.value.isDeleteBottomSheetVisible)
    }
}