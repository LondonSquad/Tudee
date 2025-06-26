package com.london.tudee.data.mappers

import com.london.tudee.data.local.roomdb.dto.CategoryDto
import com.london.tudee.domain.entities.Category

fun CategoryDto.convertToCategory(): Category {
    return Category(
        id = this.id,
        title = this.name,
        isDefault = this.isDefault,
        iconRes = this.iconRes,
        taskCount = this.taskCount,
    )
}

fun Category.convertToCategoryDto(): CategoryDto {
    return CategoryDto(
        id = this.id,
        name = this.title,
        isDefault = this.isDefault,
        iconRes = this.iconRes,
        taskCount = this.taskCount,
    )
}