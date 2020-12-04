package com.example.cft_converter

import android.app.Application
import com.example.cft_converter.di.DependencyFactory
import io.realm.Realm
import io.realm.RealmConfiguration

@Suppress("unused")
open class App: Application() {

    lateinit var dependencyFactory: DependencyFactory private set

    override fun onCreate() {
        super.onCreate()
        dependencyFactory = DependencyFactory()

        Realm.init(this)
        val configuration: RealmConfiguration = RealmConfiguration.Builder().build()
        Realm.setDefaultConfiguration(configuration)
    }
}