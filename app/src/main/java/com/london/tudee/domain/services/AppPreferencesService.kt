package com.london.tudee.domain.services

import androidx.compose.runtime.MutableState

interface AppPreferencesService {
    val hasOnboardingBeenShown: Boolean
    val isDarkModeEnabled: MutableState<Boolean?>
    fun setOnBoardingShown()
    fun setDarkModeEnabled(isEnabled: Boolean)
}