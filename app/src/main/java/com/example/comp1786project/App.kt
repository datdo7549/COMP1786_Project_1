package com.example.comp1786project

import android.app.Application
import com.example.comp1786project.local.di.databaseModule
import com.example.comp1786project.local.di.localModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(
                localModule,
                databaseModule
            )
        }
    }
}