package com.london.tudee.data.local.roomdb

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Base64
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.createBitmap
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
    val bitmap = createBitmap(width, height)
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)
    return bitmap
}

fun defaultCategory(context: Context) = listOf(
    Category(
        id = 0,
        title = context.getString(R.string.education),
        iconRes = convertDrawableToByteArray(context, R.drawable.ic_education),
        isDefault = true,
        taskCount = 0
    ),
    Category(
        id = 0,
        title = context.getString(R.string.shopping),
        iconRes = convertDrawableToByteArray(context, R.drawable.ic_shopping),
        isDefault = true,
        taskCount = 0
    ),
    Category(
        id = 0,
        title = context.getString(R.string.medical),
        iconRes = convertDrawableToByteArray(context, R.drawable.ic_medical),
        isDefault = true,
        taskCount = 0
    ),
    Category(
        id = 0,
        title = context.getString(R.string.gym),
        iconRes = convertDrawableToByteArray(context, R.drawable.ic_gym),
        isDefault = true,
        taskCount = 0
    ),
    Category(
        id = 0,
        title = context.getString(R.string.entertainment),
        iconRes = convertDrawableToByteArray(context, R.drawable.ic_entertainment),
        isDefault = true,
        taskCount = 0
    ),
    Category(
        id = 0,
        title = context.getString(R.string.cooking),
        iconRes = convertDrawableToByteArray(context, R.drawable.ic_cooking),
        isDefault = true,
        taskCount = 0
    ),
    Category(
        id = 0,
        title = context.getString(R.string.family_friend),
        iconRes = convertDrawableToByteArray(context, R.drawable.ic_family),
        isDefault = true,
        taskCount = 0
    ),
    Category(
        id = 0,
        title = context.getString(R.string.traveling),
        iconRes = convertDrawableToByteArray(context, R.drawable.ic_travel),
        isDefault = true,
        taskCount = 0
    ),
    Category(
        id = 0,
        title = context.getString(R.string.agriculture),
        iconRes = convertDrawableToByteArray(context, R.drawable.ic_agriculture),
        isDefault = true,
        taskCount = 0
    ),
    Category(
        id = 0,
        title = context.getString(R.string.coding),
        iconRes = convertDrawableToByteArray(context, R.drawable.ic_coding),
        isDefault = true,
        taskCount = 0
    ),
    Category(
        id = 0,
        title = context.getString(R.string.adoration),
        iconRes = convertDrawableToByteArray(context, R.drawable.ic_adoration),
        isDefault = true,
        taskCount = 0
    ),
    Category(
        id = 0,
        title = context.getString(R.string.fixing_bugs),
        iconRes = convertDrawableToByteArray(context, R.drawable.ic_bug_fix),
        isDefault = true,
        taskCount = 0
    ),
    Category(
        id = 0,
        title = context.getString(R.string.cleaning),
        iconRes = convertDrawableToByteArray(context, R.drawable.ic_cleaning),
        isDefault = true,
        taskCount = 0
    ),
    Category(
        id = 0,
        title = context.getString(R.string.work),
        iconRes = convertDrawableToByteArray(context, R.drawable.ic_work),
        isDefault = true,
        taskCount = 0
    ),
    Category(
        id = 0,
        title = context.getString(R.string.budgeting),
        iconRes = convertDrawableToByteArray(context, R.drawable.ic_budgeting),
        isDefault = true,
        taskCount = 0
    ),
    Category(
        id = 0,
        title = context.getString(R.string.self_care),
        iconRes = convertDrawableToByteArray(context, R.drawable.ic_self_care),
        isDefault = true,
        taskCount = 0
    ),
    Category(
        id = 0,
        title = context.getString(R.string.event),
        iconRes = convertDrawableToByteArray(context, R.drawable.ic_event),
        isDefault = true,
        taskCount = 0
    )
)