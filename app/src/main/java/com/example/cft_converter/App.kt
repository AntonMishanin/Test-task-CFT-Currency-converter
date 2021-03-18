package com.example.cft_converter

import android.app.Application
import com.example.cft_converter.di.CurrencyComponent
import com.example.cft_converter.di.DaggerCurrencyComponent
import com.example.cft_converter.di.LocalDataSourceModule

open class App : Application() {

    lateinit var currencyComponent: CurrencyComponent
        private set

    override fun onCreate() {
        super.onCreate()

        currencyComponent = DaggerCurrencyComponent.builder()
            .localDataSourceModule(LocalDataSourceModule(this))
            .build()
    }
}