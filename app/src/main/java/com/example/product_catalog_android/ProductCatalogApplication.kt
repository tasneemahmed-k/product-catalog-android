package com.example.product_catalog_android

import android.app.Application
import com.example.data.di.networkModule
import com.example.data.di.repositoryModule
import com.example.product_catalog_android.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ProductCatalogApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin() {
//            gives Koin access to the Android application context.
            androidContext(this@ProductCatalogApplication)

            modules(
                networkModule,
                repositoryModule,
                appModule
            )
        }
    }
}