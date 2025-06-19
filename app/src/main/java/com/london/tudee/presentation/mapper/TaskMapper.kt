package com.london.tudee.presentation.mapper

import com.london.tudee.domain.entities.Task
import com.london.tudee.presentation.model.TaskUiState
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Task.toPresentation(): TaskUiState {
    return TaskUiState(
        id = this.id,
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