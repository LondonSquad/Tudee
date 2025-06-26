package com.london.tudee.presentation.utils

import android.content.Context
import com.london.tudee.R
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

object HomeScreenUtils {
    fun customDateFormatter(context: Context): String {
        val currentDate = Clock.System.now()
            .toLocalDateTime(TimeZone.currentSystemDefault())
            .date

        val day = currentDate.dayOfMonth
        val monthName = getMonthName(currentDate.monthNumber, context)
        val year = currentDate.year

        return "$day $monthName $year"
    }

    private fun getMonthName(monthNumber: Int, context: Context): String {
        val resId = when (monthNumber) {
            1 -> R.string.Jan
            2 -> R.string.Feb
            3 -> R.string.Mar
            4 -> R.string.Apr
            5 -> R.string.May
            6 -> R.string.Jun
            7 -> R.string.Jul
            8 -> R.string.Aug
            9 -> R.string.Sep
            10 -> R.string.Oct
            11 -> R.string.Nov
            12 -> R.string.Dec
            else -> R.string.Jan
        }
        return context.getString(resId)
    }

}