package com.treeforcom.koin_sample

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class KoinApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(appModules).androidContext(applicationContext)
        }
    }
}