package com.london.tudee.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.london.tudee.domain.services.AppPreferencesService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


val Context.appPreferencesDataStore: DataStore<Preferences> by preferencesDataStore(name = "app_preferences")

class AppPreferencesServiceImpl(
    private val dataStore: DataStore<Preferences> // Koin will provide this
) : AppPreferencesService {

    private object PreferencesKeys {
        val HAS_ONBOARDING_BEEN_SHOWN = booleanPreferencesKey("has_onboarding_been_shown")
    }

    override fun hasOnboardingBeenShown(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[PreferencesKeys.HAS_ONBOARDING_BEEN_SHOWN] ?: false
        }
    }

    override suspend fun setOnboardingShown() {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.HAS_ONBOARDING_BEEN_SHOWN] = true
        }
    }
}