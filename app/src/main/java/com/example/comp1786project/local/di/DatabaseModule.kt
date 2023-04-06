package com.example.comp1786project.local.di

import androidx.room.Room
import com.example.comp1786project.local.database.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

const val APP_DATABASE_NAME = "app-db"
val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            APP_DATABASE_NAME
        ).allowMainThreadQueries().build()
    }
    single { get<AppDatabase>().userDao() }
    single { get<AppDatabase>().tripDao() }
    single { get<AppDatabase>().expenseDao() }
}