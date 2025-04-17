package com.immortalidiot.geochat

import android.app.Application
import cafe.adriel.voyager.core.registry.ScreenRegistry
import di.authModule
import di.authScreenModule
import di.chatsScreenModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

internal class GeoChatApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@GeoChatApplication)
            modules(authModule)
        }

        ScreenRegistry {
            authScreenModule
            chatsScreenModule
        }
    }
}
