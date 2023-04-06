package com.example.comp1786project.local.di

import com.example.comp1786project.local.prefs.AppPrefs
import com.example.comp1786project.util.BiometricSecurityUtils
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val localModule = module {
    single { AppPrefs(androidContext()) }
    singleOf(::BiometricSecurityUtils)
}