package com.example.sessiontesttask.di

import android.app.Activity
import android.hardware.SensorManager
import androidx.room.Room
import com.example.sessiontesttask.data.AppDatabase
import com.example.sessiontesttask.presentation.viewmodels.SessionViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(androidApplication(), AppDatabase::class.java, "app_database")
            .build()
    }
    single { get<AppDatabase>().sessionDao() }
    single { get<AppDatabase>().timeStampDao() }
}

val viewModelModule = module {
    viewModel { SessionViewModel(get(), get(), get()) }
}

val sensorModule = module {
    single { androidApplication().getSystemService(Activity.SENSOR_SERVICE) as? SensorManager }
}

