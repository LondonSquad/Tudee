package com.london.tudee.domain.entities

//@Dao
data class Category(
    val id: Int,
    val title:String,
    val imageRes: Int, // val imageRes: ByteArray, bitmap
    val task : List<Task> // val task : List<Int>
)
