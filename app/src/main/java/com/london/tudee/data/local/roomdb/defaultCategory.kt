package com.london.tudee.data.local.roomdb

import android.content.Context
import com.london.tudee.R
import com.london.tudee.domain.entities.Category
import com.london.tudee.presentation.utils.convertDrawableResToBitmap
import com.london.tudee.presentation.utils.saveImageToInternalStorage

fun defaultCategory(context: Context) = listOf(
    Category(
        titleRes = R.string.education,
        title = null,
        iconRes = saveImageToInternalStorage(
            context,
            convertDrawableResToBitmap(context, R.drawable.ic_education),
            "education"
        ),
        isDefault = true,
        taskCount = 0
    ),
    Category(
        titleRes = R.string.shopping,
        title = null,
        iconRes = saveImageToInternalStorage(
            context,
            convertDrawableResToBitmap(context, R.drawable.ic_shopping),
            "shopping"
        ),
        isDefault = true,
        taskCount = 0
    ),
    Category(
        titleRes = R.string.medical,
        title = null,
        iconRes = saveImageToInternalStorage(
            context,
            convertDrawableResToBitmap(context, R.drawable.ic_medical),
            "medical"
        ),
        isDefault = true,
        taskCount = 0
    ),
    Category(
        titleRes = R.string.gym,
        title = null,
        iconRes = saveImageToInternalStorage(
            context,
            convertDrawableResToBitmap(context, R.drawable.ic_gym),
            "gym"
        ),
        isDefault = true,
        taskCount = 0
    ),
    Category(
        titleRes = R.string.entertainment,
        title = null,
        iconRes = saveImageToInternalStorage(
            context,
            convertDrawableResToBitmap(context, R.drawable.ic_entertainment),
            "entertainment"
        ),
        isDefault = true,
        taskCount = 0
    ),
    Category(
        titleRes = R.string.cooking,
        title = null,
        iconRes = saveImageToInternalStorage(
            context,
            convertDrawableResToBitmap(context, R.drawable.ic_cooking),
            "cooking"
        ),
        isDefault = true,
        taskCount = 0
    ),
    Category(
        titleRes = R.string.family_friend,
        title = null,
        iconRes = saveImageToInternalStorage(
            context,
            convertDrawableResToBitmap(context, R.drawable.ic_family),
            "family_friend"
        ),
        isDefault = true,
        taskCount = 0
    ),
    Category(
        titleRes = R.string.traveling,
        title = null,
        iconRes = saveImageToInternalStorage(
            context,
            convertDrawableResToBitmap(context, R.drawable.ic_travel),
            "traveling"
        ),
        isDefault = true,
        taskCount = 0
    ),
    Category(
        titleRes = R.string.agriculture,
        title = null,
        iconRes = saveImageToInternalStorage(
            context,
            convertDrawableResToBitmap(context, R.drawable.ic_agriculture),
            "agriculture"
        ),
        isDefault = true,
        taskCount = 0
    ),
    Category(
        titleRes = R.string.coding,
        title = null,
        iconRes = saveImageToInternalStorage(
            context,
            convertDrawableResToBitmap(context, R.drawable.ic_coding),
            "coding"
        ),
        isDefault = true,
        taskCount = 0
    ),
    Category(
        titleRes = R.string.adoration,
        title = null,
        iconRes = saveImageToInternalStorage(
            context,
            convertDrawableResToBitmap(context, R.drawable.ic_adoration),
            "adoration"
        ),
        isDefault = true,
        taskCount = 0
    ),
    Category(
        titleRes = R.string.fixing_bugs,
        title = null,
        iconRes = saveImageToInternalStorage(
            context,
            convertDrawableResToBitmap(context, R.drawable.ic_bug_fix),
            "fixing_bugs"
        ),
        isDefault = true,
        taskCount = 0
    ),
    Category(
        titleRes = R.string.cleaning,
        title = null,
        iconRes = saveImageToInternalStorage(
            context,
            convertDrawableResToBitmap(context, R.drawable.ic_cleaning),
            "cleaning"
        ),
        isDefault = true,
        taskCount = 0
    ),
    Category(
        titleRes = R.string.work,
        title = null,
        iconRes = saveImageToInternalStorage(
            context,
            convertDrawableResToBitmap(context, R.drawable.ic_work),
            "work"
        ),
        isDefault = true,
        taskCount = 0
    ),
    Category(
        titleRes = R.string.budgeting,
        title = null,
        iconRes = saveImageToInternalStorage(
            context,
            convertDrawableResToBitmap(context, R.drawable.ic_budgeting),
            "budgeting"
        ),
        isDefault = true,
        taskCount = 0
    ),
    Category(
        titleRes = R.string.self_care,
        title = null,
        iconRes = saveImageToInternalStorage(
            context,
            convertDrawableResToBitmap(context, R.drawable.ic_self_care),
            "self_care"
        ),
        isDefault = true,
        taskCount = 0
    ),
    Category(
        titleRes = R.string.event,
        title = null,
        iconRes = saveImageToInternalStorage(
            context,
            convertDrawableResToBitmap(context, R.drawable.ic_event),
            "event"
        ),
        isDefault = true,
        taskCount = 0
    )
)