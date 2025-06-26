package com.london.tudee.data.preferences

import android.content.SharedPreferences
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.edit
import com.london.tudee.data.preferences.AppPreferencesServiceImpl.PreferencesKeys.DARK_MODE_ENABLED
import com.london.tudee.data.preferences.AppPreferencesServiceImpl.PreferencesKeys.HAS_ONBOARDING_BEEN_SHOWN
import com.london.tudee.domain.services.AppPreferencesService
import org.koin.core.annotation.Single

@Single
class AppPreferencesServiceImpl(
    private val preferences: SharedPreferences,
) : AppPreferencesService {

    override val hasOnboardingBeenShown: Boolean =
        preferences.getBoolean(HAS_ONBOARDING_BEEN_SHOWN, false)

    override val isDarkModeEnabled: MutableState<Boolean?> = mutableStateOf(
        getDefaultIsDarkModeValue()
    )

    private fun getDefaultIsDarkModeValue(): Boolean? {
        return if (preferences.contains(DARK_MODE_ENABLED))
            preferences.getBoolean(
                /* key = */ DARK_MODE_ENABLED,
                /* defValue = */ false
            )
        else null
    }

    override fun setOnboardingShown() {
        preferences.edit { putBoolean(HAS_ONBOARDING_BEEN_SHOWN, true) }
    }

    override fun setDarkModeEnabled(isEnabled: Boolean) {
        isDarkModeEnabled.value = isEnabled
        preferences.edit { putBoolean(DARK_MODE_ENABLED, isEnabled) }
    }

    private object PreferencesKeys {
        const val HAS_ONBOARDING_BEEN_SHOWN = "has_onboarding_been_shown"
        const val DARK_MODE_ENABLED = "dark_mode_enabled"
    }
}