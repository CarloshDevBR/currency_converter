package com.example.currencyconverter.domain.usecase

import com.example.currencyconverter.domain.model.CurrencyConversionData
import com.example.currencyconverter.domain.repository.CurrencyRepository
import javax.inject.Inject

interface CurrencyConversionUseCase {
    suspend operator fun invoke(params: Params): Result<CurrencyConversionData>

    data class Params(
        val fromCurrency: String,
        val toCurrency: String,
        val amount: Double
    )
}

class CurrencyConversionUseCaseImpl @Inject constructor(
    private val repository: CurrencyRepository
) : CurrencyConversionUseCase {
    override suspend fun invoke(params: CurrencyConversionUseCase.Params): Result<CurrencyConversionData> =
        repository.convertCurrency(
            fromCurrency = params.fromCurrency,
            toCurrency = params.toCurrency,
            amount = params.amount
        )
}