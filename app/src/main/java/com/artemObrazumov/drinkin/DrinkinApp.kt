package com.artemObrazumov.drinkin

import android.app.Application
import com.artemObrazumov.drinkin.di.appModule
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