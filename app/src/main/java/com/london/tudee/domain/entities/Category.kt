package com.london.tudee.domain.entities

data class Category(
    val id: Int = 0,
    val titleRes: Int? = null,
    val title: String? = null,
    val iconRes: String,
    val isDefault: Boolean,
    val taskCount: Int,
)