package com.example.currencyconverter.data.repository

import com.example.currencyconverter.data.mapper.toCurrencyConversionData
import com.example.currencyconverter.data.network.client.KtorClient
import com.example.currencyconverter.domain.model.CurrencyConversionData
import com.example.currencyconverter.domain.repository.CurrencyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    val ktorClient: KtorClient
) : CurrencyRepository {
    override suspend fun convertCurrency(
        fromCurrency: String,
        toCurrency: String,
        amount: Double
    ): Result<CurrencyConversionData> =
        try {
            Result.success(
                ktorClient.convertCurrency(
                    fromCurrency = fromCurrency,
                    toCurrency = toCurrency,
                    amount = amount
                ).toCurrencyConversionData()
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
}