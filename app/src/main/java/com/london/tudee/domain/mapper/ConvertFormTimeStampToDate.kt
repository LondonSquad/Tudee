package com.london.tudee.domain.mapper

import kotlinx.datetime.Instant

fun Long.convertToInstant(): Instant = Instant.fromEpochMilliseconds(this)

fun Instant.convertToMillis(): Long = this.toEpochMilliseconds()
