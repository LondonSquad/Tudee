package com.london.tudee.data.local.roomdb.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.london.tudee.data.local.roomdb.dto.CategoryDtoDefaults.DEFAULT_ICON

@Entity(tableName = "CATEGORY_TABLE")
data class CategoryDto(
    @PrimaryKey(autoGenerate = true)
    val id: Int = DEFAULT_ICON,
    val titleRes: Int? = null,
    val title: String? = null,
    val iconRes: String,
    val isDefault: Boolean,
    val taskCount: Int
)

private object CategoryDtoDefaults {
    const val DEFAULT_ICON = 0
}