package com.london.tudee.data.local.roomdb

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Base64
import androidx.appcompat.content.res.AppCompatResources
import com.london.tudee.R
import com.london.tudee.domain.entities.Category
import java.io.ByteArrayOutputStream

fun convertDrawableToByteArray(context: Context, drawableResId: Int): String {
    val drawable = AppCompatResources.getDrawable(context, drawableResId)
        ?: throw IllegalArgumentException("Drawable resource ID $drawableResId could not be found.")

    val bitmap = drawableToBitmap(drawable)
    val outputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
    val byteArray = outputStream.toByteArray()
    return Base64.encodeToString(byteArray, Base64.DEFAULT)
}

private fun drawableToBitmap(drawable: Drawable): Bitmap {
    if (drawable is BitmapDrawable) {
        return drawable.bitmap
    }
    val width = drawable.intrinsicWidth.takeIf { it > 0 } ?: 100
    val height = drawable.intrinsicHeight.takeIf { it > 0 } ?: 100
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)
    return bitmap
}

fun defaultCategory(context: Context) = listOf(
    Category(
        id = 0,
        title = "Education",
        iconRes = convertDrawableToByteArray(context, R.drawable.ic_education),
        isDefault = true,
        taskCount = 0
    ),
    Category(
        id = 0,
        title = "Shopping",
        iconRes = convertDrawableToByteArray(context, R.drawable.ic_shopping),
        isDefault = true,
        taskCount = 0
    ),
    Category(
        id = 0,
        title = "Medical",
        iconRes = convertDrawableToByteArray(context, R.drawable.ic_medical),
        isDefault = true,
        taskCount = 0
    ),
    Category(
        id = 0,
        title = "Gym",
        iconRes = convertDrawableToByteArray(context, R.drawable.ic_gym),
        isDefault = true,
        taskCount = 0
    ),
    Category(
        id = 0,
        title = "Entertainment",
        iconRes = convertDrawableToByteArray(context, R.drawable.ic_entertainment),
        isDefault = true,
        taskCount = 0
    ),
    Category(
        id = 0,
        title = "Cooking",
        iconRes = convertDrawableToByteArray(context, R.drawable.ic_cooking),
        isDefault = true,
        taskCount = 0
    ),
    Category(
        id = 0,
        title = "Family & friend",
        iconRes = convertDrawableToByteArray(context, R.drawable.ic_family),
        isDefault = true,
        taskCount = 0
    ),
    Category(
        id = 0,
        title = "Traveling",
        iconRes = convertDrawableToByteArray(context, R.drawable.ic_travel),
        isDefault = true,
        taskCount = 0
    ),
    Category(
        id = 0,
        title = "Agriculture",
        iconRes = convertDrawableToByteArray(context, R.drawable.ic_agriculture),
        isDefault = true,
        taskCount = 0
    ),
    Category(
        id = 0,
        title = "Coding",
        iconRes = convertDrawableToByteArray(context, R.drawable.ic_coding),
        isDefault = true,
        taskCount = 0
    ),
    Category(
        id = 0,
        title = "Adoration",
        iconRes = convertDrawableToByteArray(context, R.drawable.ic_adoration),
        isDefault = true,
        taskCount = 0
    ),
    Category(
        id = 0,
        title = "Fixing bugs",
        iconRes = convertDrawableToByteArray(context, R.drawable.ic_bug_fix),
        isDefault = true,
        taskCount = 0
    ),
    Category(
        id = 0,
        title = "Cleaning",
        iconRes = convertDrawableToByteArray(context, R.drawable.ic_cleaning),
        isDefault = true,
        taskCount = 0
    ),
    Category(
        id = 0,
        title = "Work",
        iconRes = convertDrawableToByteArray(context, R.drawable.ic_work),
        isDefault = true,
        taskCount = 0
    ),
    Category(
        id = 0,
        title = "Budgeting",
        iconRes = convertDrawableToByteArray(context, R.drawable.ic_budgeting),
        isDefault = true,
        taskCount = 0
    ),
    Category(
        id = 0,
        title = "Self-care",
        iconRes = convertDrawableToByteArray(context, R.drawable.ic_self_care),
        isDefault = true,
        taskCount = 0
    ),
    Category(
        id = 0,
        title = "Event",
        iconRes = convertDrawableToByteArray(context, R.drawable.ic_event),
        isDefault = true,
        taskCount = 0
    )
)