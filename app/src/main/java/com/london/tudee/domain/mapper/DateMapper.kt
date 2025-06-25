package com.london.tudee.domain.mapper

import kotlinx.datetime.Instant

fun Long.toInstant(): Instant = Instant.fromEpochMilliseconds(this)

fun Instant.toMillis(): Long = this.toEpochMilliseconds()
