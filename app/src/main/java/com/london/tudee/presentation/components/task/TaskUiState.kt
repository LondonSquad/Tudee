package com.london.tudee.presentation.components.task

import androidx.annotation.DrawableRes
import com.london.tudee.domain.entities.Priority

data class TaskUiState(
    val priority: Priority,
    @DrawableRes val iconResId: Int,
    val title: String,
    val description: String,
    val date: String? = null
)