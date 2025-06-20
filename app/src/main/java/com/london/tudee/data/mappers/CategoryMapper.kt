package com.london.tudee.data.mappers

import com.london.tudee.data.local.room_db.dto.CategoryDto
import com.london.tudee.domain.entities.Category

fun CategoryDto.convertToCategory(): Category {
    return Category(
        id = this.id,
        name = this.name,
        arName = this.arName,
        isDefault = this.isDefault,
        iconRes = this.iconRes,
        taskCount = this.taskCount,
        tint = this.tint
    )
}

fun Category.convertToCategoryDto(): CategoryDto {
    return CategoryDto(
        id = this.id,
        name = this.name,
        arName = this.arName,
        isDefault = this.isDefault,
        iconRes = this.iconRes,
        taskCount = this.taskCount,
        tint = this.tint
    )
}