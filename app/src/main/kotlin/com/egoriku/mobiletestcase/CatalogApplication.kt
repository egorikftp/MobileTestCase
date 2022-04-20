@file:Suppress("unused")

package com.egoriku.mobiletestcase

import android.app.Application
import com.egoriku.database.koin.databaseModule
import com.egoriku.mobiletestcase.list.koin.catalogModule
import com.egoriku.network.koin.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CatalogApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@CatalogApplication)
            modules(
                catalogModule,
                databaseModule,
                networkModule
            )
        }
    }
}