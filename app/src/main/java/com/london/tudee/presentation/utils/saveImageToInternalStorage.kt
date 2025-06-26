package com.london.tudee.presentation.utils

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import java.io.File
import java.io.FileOutputStream

fun saveImageToInternalStorage(context: Context, bitmap: Bitmap?, fileName: String): String {
    val file = File(context.filesDir, fileName + System.currentTimeMillis())
    FileOutputStream(file).use { outputStream ->
        bitmap?.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
    }
    return Uri.fromFile(file).toString()
}