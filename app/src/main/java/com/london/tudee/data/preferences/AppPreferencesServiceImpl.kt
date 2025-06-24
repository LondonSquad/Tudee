package com.london.tudee.data.preferences

import android.content.SharedPreferences
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.edit
import com.london.tudee.domain.services.AppPreferencesService

class AppPreferencesServiceImpl(
    private val preferences: SharedPreferences,
) : AppPreferencesService {

    override val hasOnboardingBeenShown: Boolean =
        preferences.getBoolean(PreferencesKeys.HAS_ONBOARDING_BEEN_SHOWN, false)

    override val isDarkModeEnabled: MutableState<Boolean> = mutableStateOf(
        preferences.getBoolean(
            PreferencesKeys.DARK_MODE_ENABLED,
            false
        )
    )

    override fun setOnboardingShown() {
        preferences.edit { putBoolean(PreferencesKeys.HAS_ONBOARDING_BEEN_SHOWN, true) }
    }

    override fun setDarkModeEnabled(isEnabled: Boolean) {
        isDarkModeEnabled.value = isEnabled
        preferences.edit { putBoolean(PreferencesKeys.DARK_MODE_ENABLED, isEnabled) }
    }

    private object PreferencesKeys {
        const val HAS_ONBOARDING_BEEN_SHOWN = "has_onboarding_been_shown"
        const val DARK_MODE_ENABLED = "dark_mode_enabled"
    }
}