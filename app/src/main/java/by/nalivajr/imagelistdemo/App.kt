package by.nalivajr.imagelistdemo

import android.app.Application
import by.nalivajr.imagelistdemo.di.domainModule
import by.nalivajr.imagelistdemo.di.networkModule
import by.nalivajr.imagelistdemo.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(
                networkModule,
                domainModule,
                viewModelModule
            )
        }
    }
}