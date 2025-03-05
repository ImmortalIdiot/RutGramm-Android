package com.immortalidiot.geochat

import android.app.Application
import di.authModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class GeoChatApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@GeoChatApp)
            modules(authModule)
        }
    }
}
