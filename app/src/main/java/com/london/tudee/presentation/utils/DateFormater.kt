package com.london.tudee.presentation.utils

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun formatDate(instant: Instant): String {
    val date = instant.toLocalDateTime(TimeZone.currentSystemDefault()).date
    val day = date.dayOfMonth.toString().padStart(2, '0')
    val month = date.monthNumber.toString().padStart(2, '0')
    val year = date.year.toString()
    return "$day-$month-$year"
}

object DateFormatter {

    private val timeZone = TimeZone.currentSystemDefault()

    fun Long.toMonthShort(): String {
        val dt = Instant.fromEpochMilliseconds(this).toLocalDateTime(timeZone)
        return dt.month.name.take(3).lowercase().replaceFirstChar { it.uppercase() } //  Jun
    }

    fun Long.toYear(): String {
        val dt = Instant.fromEpochMilliseconds(this).toLocalDateTime(timeZone)
        return dt.year.toString() // 1999
    }

    fun Long.toDayNumber(): String {
        val dt = Instant.fromEpochMilliseconds(this).toLocalDateTime(timeZone)
        return dt.dayOfMonth.toString().padStart(2, '0') // 25
    }

    fun Long.toDayOfWeekShort(): String {
        val dt = Instant.fromEpochMilliseconds(this).toLocalDateTime(timeZone)
        return dt.dayOfWeek.name.take(3).lowercase().replaceFirstChar { it.uppercase() } //  Mon
    }
}