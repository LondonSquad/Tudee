package com.london.tudee.data.mappers

import com.london.tudee.data.local.roomdb.dto.CategoryDto
import com.london.tudee.domain.entities.Category
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class CategoryMappersTest {
    @Test
    fun `when convert CategoryDto to convertToCategory returns Category`() {
        //Given & When
        val result = testCategoryDto().convertToCategory()
        //Then
        Assertions.assertEquals(result, testCategory())
    }

    @Test
    fun `when convert Category to convertToCategoryDto returns CategoryDto`() {
        //Given & When
        val result = testCategory().convertToCategoryDto()
        //Then
        Assertions.assertEquals(result, testCategoryDto())
    }

    private fun testCategoryDto() = CategoryDto(
        id = 1, title = "Work", isDefault = true, iconRes = "ic_work", taskCount = 5
    )

    private fun testCategory() = Category(
        id = 1, title = "Work", isDefault = true, iconRes = "ic_work", taskCount = 5
    )
}