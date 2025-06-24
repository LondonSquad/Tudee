package com.london.tudee.domain.services

interface AppPreferencesService {
    val hasOnboardingBeenShown: Boolean
    var isDarkModeEnabled: Boolean
    fun setOnboardingShown()
    fun setDarkModeEnabled(isEnabled: Boolean)
}