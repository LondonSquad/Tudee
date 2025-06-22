package com.london.tudee.presentation.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64

fun converterStringToBitmap(encodedString: String): Bitmap? {
    return try {
        val encodeByte = Base64.decode(encodedString, Base64.DEFAULT)
        BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
    } catch (e:Exception) {
        e.printStackTrace()
        null
    }
}