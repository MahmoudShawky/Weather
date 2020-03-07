package com.shawky.openweather

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.shawky.openweather.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class WeatherApp : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    private fun initKoin() {
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@WeatherApp)
            modules(
                listOf(
                    repositoryModule,
                    viewModelModule,
                    retrofitModule,
                    databaseModule,
                    apiModule
                )
            )
        }
    }
}