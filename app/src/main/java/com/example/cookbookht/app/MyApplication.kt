package com.example.cookbookht.app

import android.app.Application
import com.example.cookbookht.module.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(
                listOf(
                    apiModule,
                    retrofitModule,
                    storageModule,
                    viewModelModule,
                    repositoryModule,
                    dataSourceModule,
                    appModule
                )
            )
        }
    }
}
