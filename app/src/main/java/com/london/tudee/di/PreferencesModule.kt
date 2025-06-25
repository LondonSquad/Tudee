package com.london.tudee.di

import android.content.Context
import android.content.SharedPreferences
import com.london.tudee.data.preferences.AppPreferencesServiceImpl
import com.london.tudee.domain.services.AppPreferencesService
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
class PreferencesModule {
    @Single
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
    }

    @Single
    fun provideAppPreferencesService(sharedPreferences: SharedPreferences): AppPreferencesService {
        return AppPreferencesServiceImpl(sharedPreferences)
    }
}