package com.london.tudee.presentation.components.priority

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color

data class PriorityResources(
    @DrawableRes val iconResId: Int,
    @StringRes val textResId: Int,
    val backgroundColor: Color
)