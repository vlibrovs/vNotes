package com.vlibrovs.vnotesfinal.ui.application

import android.app.Application
import com.vlibrovs.vnotesfinal.other.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class VNoteApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@VNoteApplication)
            modules(appModule)
        }
    }

}