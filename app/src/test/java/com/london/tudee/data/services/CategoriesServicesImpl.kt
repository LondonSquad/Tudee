package com.london.tudee.data.services

import com.london.tudee.R
import com.london.tudee.data.exception.AddCategoryException
import com.london.tudee.data.exception.CategoryNotFoundException
import com.london.tudee.data.exception.DeleteCategoryException
import com.london.tudee.data.exception.EditCategoryException
import com.london.tudee.data.exception.GetAllCategoriesException
import com.london.tudee.data.exception.GetCategoryIconException
import com.london.tudee.data.local.roomdb.dao.CategoryDao
import com.london.tudee.data.local.roomdb.dto.CategoryDto
import com.london.tudee.data.mappers.convertToCategory
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CategoriesServicesImplTest {
    private lateinit var categoryDao: CategoryDao
    private lateinit var categoriesServicesImpl: CategoriesServicesImpl

    @BeforeEach
    fun setup() {
        categoryDao = mockk(relaxed = true)
        categoriesServicesImpl = CategoriesServicesImpl(categoryDao)
    }

    @Test
    fun `when add should insert category and verify DAO call`() = runTest {
        // Given
        val categoryDto = CategoryDto(
            id = 0,
            name = "Work",
            iconRes = R.drawable.ic_education.toString(),
            isDefault = true,
            taskCount = 0
        )
        val category = categoryDto.convertToCategory()

        // When
        categoriesServicesImpl.add(category)

        // Then
        coVerify { categoryDao.insert(categoryDto) }
    }

    @Test
    fun `when getAll should return mapped categories from DAO`() = runTest {
        // Given
        val categoryDto = CategoryDto(
            id = 1,
            name = "Study",
            iconRes = R.drawable.ic_education.toString(),
            isDefault = true,
            taskCount = 0
        )
        coEvery { categoryDao.getAll() } returns flowOf(listOf(categoryDto))

        // When
        val result = categoriesServicesImpl.getAll().first()

        // Then
        assertEquals(1, result.size)
        assertEquals("Study", result[0].title)
        assertEquals(R.drawable.ic_education.toString(), result[0].iconRes)
    }

    @Test
    fun `when getAll should return empty list when no categories exist`() = runTest {
        // Given
        coEvery { categoryDao.getAll() } returns flowOf(emptyList())

        // When
        val result = categoriesServicesImpl.getAll().first()

        // Then
        assertTrue(result.isEmpty())
    }

    @Test
    fun `when getById should return correct category`() = runTest {
        // Given
        val categoryDto = CategoryDto(
            id = 1,
            name = "Work",
            iconRes = R.drawable.ic_education.toString(),
            isDefault = true,
            taskCount = 0
        )
        coEvery { categoryDao.getById(1) } returns categoryDto

        // When
        val result = categoriesServicesImpl.getById(1)

        // Then
        assertEquals(categoryDto.convertToCategory(), result)
        coVerify { categoryDao.getById(1) }
    }

    @Test
    fun `when getById should not return incorrect category`() = runTest {
        // Given
        val expectedDto = CategoryDto(
            id = 1,
            name = "Work",
            iconRes = R.drawable.ic_education.toString(),
            isDefault = true,
            taskCount = 0
        )
        val wrongDto = CategoryDto(
            id = 1,
            name = "Wrong",
            iconRes = R.drawable.ic_education.toString(),
            isDefault = true,
            taskCount = 0
        )
        coEvery { categoryDao.getById(1) } returns expectedDto

        // When
        val result = categoriesServicesImpl.getById(1)

        // Then
        assertNotEquals(wrongDto.convertToCategory(), result)
    }

    @Test
    fun `when getIconResById should return correct icon resource`() = runTest {
        // Given
        val categoryDto = CategoryDto(
            id = 1,
            name = "Work",
            iconRes = R.drawable.ic_education.toString(),
            isDefault = true,
            taskCount = 0
        )
        coEvery { categoryDao.getIconResById(categoryDto.id) } returns R.drawable.ic_education.toString()

        // When
        val result = categoriesServicesImpl.getIconResById(1)

        // Then
        assertEquals(R.drawable.ic_education.toString(), result)
        coVerify { categoryDao.getIconResById(1) }
    }

    @Test
    fun `when delete should delete category`() = runTest {
        // Given
        val category = CategoryDto(
            id = 1,
            name = "Work",
            iconRes = R.drawable.ic_education.toString(),
            isDefault = true,
            taskCount = 0
        )

        coEvery { categoryDao.delete(category) } returns Unit
        //When
        categoriesServicesImpl.delete(category.convertToCategory())
        //then
        coVerify { categoryDao.delete(category) }
    }

    @Test
    fun `when edit should update category and verify DAO call`() = runTest {
        // Given
        val originalCategory = CategoryDto(
            id = 1,
            name = "Work",
            iconRes = R.drawable.ic_education.toString(),
            isDefault = true,
            taskCount = 0
        )

        val updatedCategory = originalCategory.copy(
            name = "Updated Work", iconRes = R.drawable.ic_work.toString()
        )

        coEvery { categoryDao.update(any()) } returns Unit
        coEvery { categoryDao.getById(1) } returns updatedCategory

        // When
        categoriesServicesImpl.edit(updatedCategory.convertToCategory())

        // Then
        coVerify { categoryDao.update(updatedCategory) }
        val result = categoriesServicesImpl.getById(1)
        assertEquals("Updated Work", result.title)
        assertEquals(R.drawable.ic_work.toString(), result.iconRes)
    }

    @Test
    fun `when add fails then throw AddCategoryException`() = runTest {
        // Given
        val category = CategoryDto(
            id = 0,
            name = "Work",
            iconRes = R.drawable.ic_work.toString(),
            isDefault = true,
            taskCount = 0
        ).convertToCategory()

        coEvery { categoryDao.insert(any()) } throws AddCategoryException()

        // When & Then
        assertThrows<AddCategoryException> {
            categoriesServicesImpl.add(category)
        }
    }

    @Test
    fun `when edit fails then throw EditCategoryException`() = runTest {
        // Given
        val category = CategoryDto(
            id = 1,
            name = "Work",
            iconRes = R.drawable.ic_work.toString(),
            isDefault = true,
            taskCount = 0
        ).convertToCategory()
        coEvery { categoryDao.update(any()) } throws EditCategoryException()
        // When & Then
        assertThrows<EditCategoryException> {
            categoriesServicesImpl.edit(category)
        }
    }

    @Test
    fun `when delete fails then throw DeleteCategoryException`() = runTest {
        // Given
        val category = CategoryDto(
            id = 1,
            name = "Work",
            iconRes = R.drawable.ic_work.toString(),
            isDefault = true,
            taskCount = 0
        ).convertToCategory()
        coEvery { categoryDao.delete(any()) } throws DeleteCategoryException()
        // When & Then
        assertThrows<DeleteCategoryException> {
            categoriesServicesImpl.delete(category)
        }
    }

    @Test
    fun `when getById fails then throw CategoryNotFoundException`() = runTest {
        // Given
        coEvery { categoryDao.getById(1) } throws CategoryNotFoundException()
        // When & Then
        assertThrows<CategoryNotFoundException> {
            categoriesServicesImpl.getById(1)
        }
    }

    @Test
    fun `when getIconResById fails then throw GetCategoryIconException`() = runTest {
        // Given
        coEvery { categoryDao.getIconResById(1) } throws GetCategoryIconException()
        // When & Then
        assertThrows<GetCategoryIconException> {
            categoriesServicesImpl.getIconResById(1)
        }
    }

    @Test
    fun `when getAll fails then throw GetAllCategoriesException`() = runTest {
        // Given
        coEvery { categoryDao.getAll() } throws GetAllCategoriesException()
        // When & Then
        assertThrows<GetAllCategoriesException> {
            categoriesServicesImpl.getAll()
        }
    }
}