package com.example.sessiontesttask

import android.app.Application
import com.example.sessiontesttask.di.databaseModule
import com.example.sessiontesttask.di.sensorModule
import com.example.sessiontesttask.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SessionApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@SessionApp)
            modules(databaseModule, viewModelModule, sensorModule)
        }
    }
}