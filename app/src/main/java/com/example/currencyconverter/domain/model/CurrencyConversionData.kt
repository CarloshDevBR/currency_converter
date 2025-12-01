package com.example.currencyconverter.domain.model

data class CurrencyConversionData(
    val baseCode: String,
    val targetCode: String,
    val conversionRate: String,
    val conversionResult: String
)