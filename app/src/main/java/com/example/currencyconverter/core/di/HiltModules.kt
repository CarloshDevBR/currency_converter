package com.example.currencyconverter.core.di

import com.example.currencyconverter.data.repository.CurrencyRepositoryImpl
import com.example.currencyconverter.domain.repository.CurrencyRepository
import com.example.currencyconverter.domain.usecase.CurrencyConversionUseCase
import com.example.currencyconverter.domain.usecase.CurrencyConversionUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface HiltModules {
    @Binds
    @Singleton
    fun bindCurrencyRepository(
        currencyRepositoryImpl: CurrencyRepositoryImpl
    ): CurrencyRepository

    @Binds
    fun bindCurrencyConversionUseCase(
        currencyConversionUseCaseImpl: CurrencyConversionUseCaseImpl
    ): CurrencyConversionUseCase
}