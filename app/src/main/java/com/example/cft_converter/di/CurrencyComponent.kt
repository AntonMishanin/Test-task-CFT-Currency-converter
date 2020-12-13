package com.example.cft_converter.di

import com.example.cft_converter.presentation.CurrencyPresenter
import dagger.Component
import moxy.presenter.ProvidePresenter
import javax.inject.Singleton

@Singleton
@Component(modules = [PresentationModule::class, RealmModule::class, RemoteDataSourceModule::class, DataModule::class, DomainModule::class])
interface CurrencyComponent {

    @ProvidePresenter
    fun getCurrencyPresenter(): CurrencyPresenter
}