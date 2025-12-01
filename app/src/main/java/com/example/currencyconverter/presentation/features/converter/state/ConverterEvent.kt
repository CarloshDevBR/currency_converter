package com.example.currencyconverter.presentation.features.converter.state

sealed interface ConverterEvent {
    data class OnFromCurrencySelected(val currency: String) : ConverterEvent
    data class OnToCurrencySelected(val currency: String) : ConverterEvent
    data class OnFromCurrencyAmountSelected(val amount: String) : ConverterEvent
    data object SendConverterForm : ConverterEvent
}