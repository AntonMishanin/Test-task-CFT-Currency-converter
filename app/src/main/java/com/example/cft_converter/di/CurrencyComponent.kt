package com.example.cft_converter.di

import com.example.cft_converter.presenters.CurrencyPresenter
import dagger.Component
import moxy.presenter.ProvidePresenter
import javax.inject.Singleton

@Singleton
@Component(modules = [LocalDataSourceModule::class, RemoteDataSourceModule::class, DataModule::class])
interface CurrencyComponent {

    @ProvidePresenter
    fun getCurrencyPresenter(): CurrencyPresenter
}