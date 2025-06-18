package com.london.tudee.domain.entities

//@Dao
data class Category(
    val id: Int,
    val title:String,
    val imageRes: Int,
    val task : List<Task>
)
