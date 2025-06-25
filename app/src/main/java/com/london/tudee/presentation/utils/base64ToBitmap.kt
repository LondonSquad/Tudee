package com.london.tudee.presentation.utils

import android.graphics.Bitmap
import android.util.Base64


// Helper function to convert Base64 string to Bitmap
 fun base64ToBitmap(base64String: String): Bitmap? {
    return try {
        if (base64String.isBlank()) return null
        val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
        android.graphics.BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}