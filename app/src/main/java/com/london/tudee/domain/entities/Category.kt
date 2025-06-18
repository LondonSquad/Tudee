package com.london.tudee.domain.entities

data class Category(
    val id: Int,
    val name: String,
    val arName: String,
    val isDefault: Boolean,
    val iconPath: String
)