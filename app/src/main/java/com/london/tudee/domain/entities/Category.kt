package com.london.tudee.domain.entities

import androidx.compose.ui.graphics.Color

data class Category(
    val id: Int,
    val name: String,
    val arName: String,
    val iconRes: Int,
    val isDefault: Boolean,
    val taskCount: Int,
    val tint: Color
)