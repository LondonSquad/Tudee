package com.london.tudee.domain.entities

data class Category(
    val id: Int,
    val title: String,
    val iconRes: String,
    val isDefault: Boolean,
    val taskCount: Int,
)