package com.example.currencyconverter.presentation.features.converter.state

data class ConverterFormUiState(
    val fromCurrenciesList: List<String> = emptyList(),
    val toCurrenciesList: List<String> = emptyList(),
    val fromCurrencySelected: String = String(),
    val toCurrencySelected: String = String(),
    val fromCurrencyAmount: String = String(),
    val toCurrencyAmount: String = String()
)