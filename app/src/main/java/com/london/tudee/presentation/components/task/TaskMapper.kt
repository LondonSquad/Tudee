package com.london.tudee.presentation.components.task

import com.london.tudee.R
import com.london.tudee.domain.entities.Task
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Task.toPresentation(): TaskUiState {
    return TaskUiState(
        priority = this.priority,
        iconResId = this.categoryId,
        title = this.title,
        description = this.description,
        date = formatDate(this.timeStamp)
    )
}

private fun formatDate(date: Date): String {
    val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    return formatter.format(date)
}