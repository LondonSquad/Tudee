package com.london.tudee.presentation.screens.home

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

object HomeScreenUtils {
    fun customDateFormatter(): String{
        val currentDate = Clock.System.now()
            .toLocalDateTime(TimeZone.currentSystemDefault())
            .date

        val day = currentDate.dayOfMonth
        val month = currentDate.month.name.lowercase().replaceFirstChar { it.uppercase() }.take(3)
        val year = currentDate.year

        return "$day $month $year"
    }
}