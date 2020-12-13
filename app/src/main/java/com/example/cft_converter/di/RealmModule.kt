package com.example.cft_converter.di

import android.content.Context
import com.example.cft_converter.data.database.RealmDb
import dagger.Module
import dagger.Provides
import io.realm.Realm
import io.realm.RealmConfiguration
import java.lang.Exception
import javax.inject.Singleton

@Module
class RealmModule(context: Context) {

    init {
        Realm.init(context)
    }

    @Provides
    fun provideRealmDb(realm: Realm): RealmDb {
        return RealmDb(realm)
    }

    @Singleton
    @Provides
    fun provideRealm(configuration: RealmConfiguration): Realm {
        return try {
            Realm.getDefaultInstance()
        } catch (e: Exception) {
            Realm.getInstance(configuration)
        }
    }

    @Singleton
    @Provides
    fun provideRealmConfiguration(): RealmConfiguration{
        return RealmConfiguration.Builder().build()
    }
}