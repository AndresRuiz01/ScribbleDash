package com.devcampus.scribbledash

import android.app.Application
import com.devcampus.scribbledash.draw.presentation.canvasModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level

class ScribbleDashApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            // Log Koin activity (useful for debugging)
            // Use Level.ERROR or Level.NONE for release builds
            androidLogger(Level.DEBUG)

            // Declare Android context
            androidContext(this@ScribbleDashApplication)

            // Load Koin modules
            modules(canvasModule)
        }
    }

}