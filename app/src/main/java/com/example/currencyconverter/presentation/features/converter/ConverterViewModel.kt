package com.example.currencyconverter.presentation.features.converter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.domain.usecase.CurrencyConversionUseCase
import com.example.currencyconverter.presentation.features.converter.state.ConverterEvent
import com.example.currencyconverter.presentation.features.converter.state.ConverterFormUiState
import com.example.currencyconverter.presentation.features.converter.state.ConverterState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConverterViewModel @Inject constructor(
    private val currencyConversionUseCase: CurrencyConversionUseCase
) : ViewModel() {
    private val _formState = MutableStateFlow(ConverterFormUiState())
    val formUiState = _formState.asStateFlow()

    private val _state = MutableStateFlow<ConverterState>(ConverterState.Idle)
    val state = _state.asStateFlow()

    init {
        _formState.update {
            it.copy(
                fromCurrenciesList = listOf("BRL", "USD", "EUR"),
                toCurrenciesList = listOf("USD", "EUR", "BRL"),
                fromCurrencySelected = "BRL",
                toCurrencySelected = "USD"
            )
        }
    }

    fun onFormEvent(event: ConverterEvent) {
        when (event) {
            is ConverterEvent.OnFromCurrencySelected -> {
                _formState.update { it.copy(fromCurrencySelected = event.currency) }
            }
            is ConverterEvent.OnToCurrencySelected -> {
                _formState.update { it.copy(toCurrencySelected = event.currency) }
            }
            is ConverterEvent.OnFromCurrencyAmountSelected -> {
                _formState.update { it.copy(fromCurrencyAmount = event.amount) }
            }
            ConverterEvent.SendConverterForm -> convertCurrency()
        }
    }

    private fun convertCurrency() {
        viewModelScope.launch {
            val fromCurrency = _formState.value.fromCurrencySelected
            val toCurrency = _formState.value.toCurrencySelected
            val amount = _formState.value.fromCurrencyAmount.toDoubleOrNull()

            if (fromCurrency.isBlank() || toCurrency.isBlank() || amount == null) {
                _state.update { ConverterState.Error("Valores inválidos") }
                return@launch
            }

            _state.update { ConverterState.Loading }

            currencyConversionUseCase.invoke(
                CurrencyConversionUseCase.Params(
                    fromCurrency = fromCurrency,
                    toCurrency = toCurrency,
                    amount = amount
                )
            ).fold(
                onSuccess = { data ->
                    _formState.update { it.copy(toCurrencyAmount = data.conversionResult) }
                    _state.update { ConverterState.Success }
                },
                onFailure = { error ->
                    _state.update { ConverterState.Error(error.message ?: "Erro desconhecido") }
                }
            )
        }
    }
}