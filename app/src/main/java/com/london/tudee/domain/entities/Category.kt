package com.london.tudee.domain.entities

data class Category(
    val id: Int,
    val title: String,
    val iconRes: String,
    val isDefault: Boolean,
    val taskCount: Int,
  //  val tint: Long
)

//categoryName = category.title,
//categoryId = category.id,
//imageUri = if (category.imageUrl.startsWith("R.drawable.")) {
//    val resourceId = category.imageUrl.toResDrawables()
//    "android.resource://com.example.tudeeapp/$resourceId".toUri()
//} else {
//    category.imageUrl.toUri()
//}