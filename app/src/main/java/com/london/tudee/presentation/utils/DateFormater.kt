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
        val dateTime = Instant.fromEpochMilliseconds(this).toLocalDateTime(timeZone)
        return dateTime.month.name.take(3).lowercase().replaceFirstChar { it.uppercase() } //  Jun
    }

    fun Long.toMonthNumber(timeZone: TimeZone = DateFormatter.timeZone): String {
        val monthValue = Instant.fromEpochMilliseconds(this)
            .toLocalDateTime(timeZone).month.value

        return monthValue.toString().padStart(2, '0') // 03
    }

    fun Long.toYear(): String {
        val dateTime = Instant.fromEpochMilliseconds(this).toLocalDateTime(timeZone)
        return dateTime.year.toString() // 1999
    }

    fun Long.toDayNumber(): String {
        val dateTime = Instant.fromEpochMilliseconds(this).toLocalDateTime(timeZone)
        return dateTime.dayOfMonth.toString().padStart(2, '0') // 25
    }

    fun Long.toDayOfWeekShort(): String {
        val dateTime = Instant.fromEpochMilliseconds(this).toLocalDateTime(timeZone)
        return dateTime.dayOfWeek.name.take(3).lowercase().replaceFirstChar { it.uppercase() } //  Mon
    }
}