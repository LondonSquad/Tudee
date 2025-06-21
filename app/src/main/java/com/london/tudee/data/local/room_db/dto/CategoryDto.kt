package com.london.tudee.data.local.room_db.dto

import androidx.compose.ui.text.LinkAnnotation
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CATEGORY_TABLE")
data class CategoryDto(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    // val arName: String,
    val iconRes: String,
    val isDefault: Boolean,
    val taskCount: Int,
)