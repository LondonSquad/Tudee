package com.london.tudee

import android.app.Application
import com.london.tudee.di.databaseModule
import com.london.tudee.di.serviceModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TudeeApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TudeeApp)
            modules(databaseModule , serviceModule)
        }
    }
}