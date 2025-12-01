package com.example.currencyconverter.data.mapper

import com.example.currencyconverter.data.network.model.CurrencyConversionResponse
import com.example.currencyconverter.domain.model.CurrencyConversionData

fun CurrencyConversionResponse.toCurrencyConversionData() =
    CurrencyConversionData(
        baseCode = this.baseCode,
        targetCode = this.targetCode,
        conversionRate = this.conversionRate.toString(),
        conversionResult = this.conversionResult.toString()
    )