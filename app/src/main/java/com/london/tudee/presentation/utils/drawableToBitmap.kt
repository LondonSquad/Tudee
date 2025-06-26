package com.london.tudee.presentation.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.createBitmap

fun convertDrawableResToBitmap(context: Context, drawableResId: Int): Bitmap {
    val drawable = AppCompatResources.getDrawable(context, drawableResId)!!
    return if (drawable is BitmapDrawable) {
        drawable.bitmap
    } else {
        val width = drawable.intrinsicWidth.takeIf { it > 0 } ?: 100
        val height = drawable.intrinsicHeight.takeIf { it > 0 } ?: 100
        val bitmap = createBitmap(width, height)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        bitmap
    }
}