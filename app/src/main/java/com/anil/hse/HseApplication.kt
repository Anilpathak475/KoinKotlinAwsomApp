package com.anil.hse

import android.app.Application
import com.anil.hse.di.networkModule
import com.anil.hse.di.persistenceModule
import com.anil.hse.di.repositoryModule
import com.anil.hse.di.viewModelModule

import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class HseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@HseApplication)
            modules(networkModule)
            modules(viewModelModule)
            modules(persistenceModule)
            modules(repositoryModule)
        }
    }
}