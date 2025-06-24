package com.london.tudee.domain.services

import androidx.compose.runtime.MutableState

interface AppPreferencesService {
    val hasOnboardingBeenShown: Boolean
    val isDarkModeEnabled: MutableState<Boolean>
    fun setOnboardingShown()
    fun setDarkModeEnabled(isEnabled: Boolean)
}