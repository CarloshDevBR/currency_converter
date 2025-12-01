package com.example.currencyconverter.domain.repository

import com.example.currencyconverter.domain.model.CurrencyConversionData

interface CurrencyRepository {
    suspend fun convertCurrency(
        fromCurrency: String,
        toCurrency: String,
        amount: Double
    ): Result<CurrencyConversionData>
}