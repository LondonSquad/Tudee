package com.london.tudee.presentation.model

import androidx.annotation.DrawableRes
import com.london.tudee.domain.entities.Priority

data class TaskUiState(
    val id: Int,
    val priority: Priority,
    @DrawableRes val iconResId: Int,
    val title: String,
    val description: String,
    val date: String? = null
)