package com.london.tudee.presentation.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri

fun galleryImageToBitmap(context: Context, imageUri: Uri): Bitmap? {
    return context.contentResolver.openInputStream(imageUri)?.use { inputStream ->
        BitmapFactory.decodeStream(inputStream)
    }
}