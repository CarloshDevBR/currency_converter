package com.example.currencyconverter.presentation.features.converter.state

sealed interface ConverterState {
    data object Idle : ConverterState
    data object Loading : ConverterState
    data object Success : ConverterState
    data class Error(val message: String) : ConverterState
}