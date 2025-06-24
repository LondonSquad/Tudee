package com.london.tudee.domain.services

import kotlinx.coroutines.flow.Flow

interface AppPreferencesService {
    fun hasOnboardingBeenShown(): Flow<Boolean>
    suspend fun setOnboardingShown()

}