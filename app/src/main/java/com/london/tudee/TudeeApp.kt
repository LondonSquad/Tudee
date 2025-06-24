package com.london.tudee

import android.app.Application
import com.london.tudee.di.DatabaseModule
import com.london.tudee.di.KoinModule
import com.london.tudee.di.ServiceModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.ksp.generated.*

class TudeeApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TudeeApp)
            modules(
                KoinModule().module, DatabaseModule().module, ServiceModule().module
            )
        }
    }
}