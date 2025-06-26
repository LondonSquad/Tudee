package com.london.tudee.presentation.screens.category

import com.london.tudee.domain.entities.Category
import com.london.tudee.domain.services.CategoryService
import io.mockk.Runs
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


@OptIn(ExperimentalCoroutinesApi::class)
class CategoriesViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: CategoriesViewModel
    private lateinit var categoryService: CategoryService

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        clearAllMocks()

        categoryService = mockk(relaxed = true)
        coEvery { categoryService.getAll() } returns flowOf(emptyList())

        viewModel = CategoriesViewModel(categoryService)
    }


    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getCategories updates uiState with categories`() = runTest {
        val expectedCategories = listOf(
            Category(1, title = "Work", iconRes = "", isDefault = false, taskCount = 2),
            Category(2, title = "Personal", iconRes = "", isDefault = false, taskCount = 4),
        )


        coEvery { categoryService.getAll() } returns flowOf(expectedCategories)

        viewModel = CategoriesViewModel(categoryService)

        advanceUntilIdle()

        val actual = viewModel.uiState.value

        assertEquals(expectedCategories, actual.categories)
        assertFalse(actual.isLoading)
    }


    @Test
    fun `createCategory success updates createCategoryUiState`() = runTest {
        val newCategory = Category(3, title = "Study", iconRes = "", isDefault = false, taskCount = 0)
        coEvery { categoryService.add(newCategory) } just Runs

        viewModel.createCategory(newCategory)
        advanceUntilIdle()

        val state = viewModel.createCategoryUiState.value
        assertTrue(state.isDeleted)
        assertFalse(state.isLoading)
    }

    @Test
    fun `createCategory failure updates createCategoryUiState with error`() = runTest {
        val newCategory = Category(4, title = "Error", iconRes = "", isDefault = false, taskCount = 5)
        val exception = RuntimeException("Failed to add")
        coEvery { categoryService.add(newCategory) } throws exception

        viewModel.createCategory(newCategory)
        advanceUntilIdle()

        val state = viewModel.createCategoryUiState.value
        assertFalse(state.isDeleted)
        assertFalse(state.isLoading)
        assertEquals("Failed to add", state.errorMessage)
    }

    @Test
    fun `setShowBottomSheet updates uiState`() {
        viewModel.setShowBottomSheet(true)
        assertTrue(viewModel.uiState.value.showBottomSheet)
    }
}

