package com.london.tudee.presentation.utils

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime

object DateFormatter {

    private val timeZone = TimeZone.currentSystemDefault()

    fun LocalDate.toLongDate() = this.atStartOfDayIn(timeZone).toEpochMilliseconds()

    fun Long.toLocalDate() = Instant.fromEpochMilliseconds(this).toLocalDateTime(timeZone).date

    fun LocalDate.toMonthNumber() = this.month.value.toString().padStart(2, '0') // ex. 03

    fun Long.toYear() = this.toLocalDate().year.toString() // 1999

    fun LocalDate.toDayNumber() = this.dayOfMonth.toString().padStart(2, '0') // ex. 25

    fun Long.toMonthShort(): String {
        val dateTime = Instant.fromEpochMilliseconds(this).toLocalDateTime(timeZone)
        return dateTime.month.name.take(3).lowercase().replaceFirstChar { it.uppercase() } // ex. Jun
    }

    fun LocalDate.toDayOfWeekShort() = this.dayOfWeek.name
        .take(3)
        .lowercase()
        .replaceFirstChar { it.uppercase() } //  Mon

    fun Instant.toFormattedDateString(): String {
        val localDate = this.toLocalDateTime(timeZone).date
        val day = localDate.toDayNumber()
        val month = localDate.toMonthNumber()
        val year = localDate.toLongDate().toYear()
        return "$day-$month-$year"
    }
}