package com.example.currencyconverter.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrencyConversionErrorResponse(
    @SerialName("result")
    val result: String,
    @SerialName("error-type")
    val errorType: String
)