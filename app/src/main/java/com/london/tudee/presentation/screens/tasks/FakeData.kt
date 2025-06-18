package com.london.tudee.presentation.screens.tasks

import androidx.compose.runtime.mutableStateListOf

val fakeDates = mutableStateListOf(
    DateItemClass("17", "Mon", false),
    DateItemClass("18", "Tue", false),
    DateItemClass("19", "Wed", false),
    DateItemClass("20", "Thu", true),
    DateItemClass("21", "Fri", false),
    DateItemClass("22", "Sat", false),
    DateItemClass("23", "Sun", false),
)

data class DateItemClass(
    val dayOfMonth: String, val dayOfWeek: String, var isSelected: Boolean
)
