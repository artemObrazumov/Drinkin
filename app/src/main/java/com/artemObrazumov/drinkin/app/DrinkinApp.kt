package com.artemObrazumov.drinkin.app

import android.app.Application
import com.artemObrazumov.drinkin.BuildConfig
import com.artemObrazumov.drinkin.app.di.appModule
import com.yandex.mapkit.MapKitFactory
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class DrinkinApp: Application() {

    override fun onCreate() {
        super.onCreate()

        // Koin
        startKoin {
            androidContext(this@DrinkinApp)
            androidLogger()

            modules(appModule)
        }

        // Yandex maps
        MapKitFactory.setApiKey(BuildConfig.MAPS_API_KEY)
    }
}