package com.london.tudee.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.london.tudee.R
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

    private fun isLeapYear(year: Int): Boolean =
        (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)

    fun getDayRangeMillis(targetDate: LocalDate): Pair<Long, Long> {
        val timeZone = TimeZone.Companion.currentSystemDefault()
        val startOfDayMillis = targetDate.atStartOfDayIn(timeZone).toEpochMilliseconds()
        val endOfDayMillis =
            targetDate.plus(DatePeriod(days = 1)).atStartOfDayIn(timeZone).toEpochMilliseconds() - 1
        return Pair(startOfDayMillis, endOfDayMillis)
    }

    @Composable
    fun String.toDayName() =
        when (this) {
            "Fri" -> stringResource(R.string.fri)
            "Sat" -> stringResource(R.string.sat)
            "Sun" -> stringResource(R.string.sun)
            "Mon" -> stringResource(R.string.mon)
            "Tue" -> stringResource(R.string.tue)
            "Wed" -> stringResource(R.string.wed)
            else -> stringResource(R.string.thu)
        }

    @Composable
    fun String.toMonthName() =
        when (this) {
            "Jan" -> stringResource(R.string.Jan)
            "Feb" -> stringResource(R.string.Feb)
            "Mar" -> stringResource(R.string.Mar)
            "Apr" -> stringResource(R.string.Apr)
            "May" -> stringResource(R.string.May)
            "Jun" -> stringResource(R.string.Jun)
            "Jul" -> stringResource(R.string.Jul)
            "Aug" -> stringResource(R.string.Aug)
            "Sep" -> stringResource(R.string.Sep)
            "Oct" -> stringResource(R.string.Oct)
            "Nov" -> stringResource(R.string.Nov)
            else -> stringResource(R.string.Dec)

        }
}