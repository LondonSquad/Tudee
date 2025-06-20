package com.london.tudee.presentation.screens.onboarding

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore("onboarding_prefs")

class OnboardingPreferences(private val context: Context) {

    private val HAS_SEEN_KEY = booleanPreferencesKey("has_seen_onboarding")

    suspend fun hasSeenOnboarding(): Boolean {
        return context.dataStore.data
            .map { prefs -> prefs[HAS_SEEN_KEY] ?: false }
            .first()
    }

    suspend fun setOnboardingSeen(seen: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[HAS_SEEN_KEY] = seen
        }
    }
}