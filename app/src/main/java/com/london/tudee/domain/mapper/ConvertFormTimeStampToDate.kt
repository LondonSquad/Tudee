package com.london.tudee.domain.mapper

import java.util.Date

fun Long.convertFromTimeStampToDate(): Date {
    return Date(this)
}

fun Date.convertFromTimeStampToDate(): Long {
    return this.time
}