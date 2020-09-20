package com.example.cft_converter

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

@Suppress("unused")
open class App: Application() {

    override fun onCreate() {
        super.onCreate()

        Realm.init(this)
        val configuration: RealmConfiguration = RealmConfiguration.Builder().build()
        Realm.setDefaultConfiguration(configuration)
    }
}