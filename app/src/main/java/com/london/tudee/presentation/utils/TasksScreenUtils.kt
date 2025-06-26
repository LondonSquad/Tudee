package com.london.tudee.presentation.utils

import com.london.tudee.presentation.utils.DateFormatter.toYear
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.plus

object TasksScreenUtils {
    fun LocalDate.lengthOfMonth(date: Long) =
        when (month) {
            Month.FEBRUARY -> {
                if (isLeapYear(date.toYear().toInt())) 29 else 28
            }

            Month.APRIL, Month.JUNE, Month.SEPTEMBER, Month.NOVEMBER -> {
                30
            }

            else -> 31
        }

    private fun isLeapYear(year: Int): Boolean = (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)

    fun getDayRangeMillis(targetDate: LocalDate): Pair<Long, Long> {
        val timeZone = TimeZone.Companion.currentSystemDefault()
        val startOfDayMillis = targetDate.atStartOfDayIn(timeZone).toEpochMilliseconds()
        val endOfDayMillis =
            targetDate.plus(DatePeriod(days = 1)).atStartOfDayIn(timeZone).toEpochMilliseconds() - 1
        return Pair(startOfDayMillis, endOfDayMillis)
    }
}