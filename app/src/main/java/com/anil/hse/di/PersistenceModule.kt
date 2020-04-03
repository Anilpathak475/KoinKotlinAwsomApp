package com.anil.hse.di

import androidx.room.Room
import com.anil.hse.R
import com.anil.hse.persistance.AppDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val persistenceModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(), AppDatabase::class.java,
            androidApplication().getString(R.string.database)
        ).build()
    }

    single { get<AppDatabase>().cartDao() }
}
