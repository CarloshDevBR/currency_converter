package com.example.currencyconverter.presentation.features.converter

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.currencyconverter.R
import com.example.currencyconverter.presentation.components.CurrencyField
import com.example.currencyconverter.presentation.features.converter.state.ConverterEvent
import com.example.currencyconverter.presentation.features.converter.state.ConverterFormUiState
import com.example.currencyconverter.presentation.features.converter.state.ConverterState
import com.example.currencyconverter.presentation.theme.CurrencyConverterTheme

@Composable
fun ConverterScreen() {
    val viewModel = viewModel<ConverterViewModel>()

    val formUiState by viewModel.formUiState.collectAsStateWithLifecycle()
    val conversionState by viewModel.state.collectAsStateWithLifecycle()

    ConverterContent(
        formUiState = formUiState,
        converterState = conversionState,
        onFormEvent = viewModel::onFormEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConverterContent(
    formUiState: ConverterFormUiState,
    converterState: ConverterState,
    onFormEvent: (ConverterEvent) -> Unit
) {
    Scaffold(
        modifier = Modifier.imePadding(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Conversor de Moedas",
                        fontWeight = FontWeight.SemiBold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .consumeWindowInsets(innerPadding)
                .systemBarsPadding(),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Column {
                    CurrencyField(
                        currencies = formUiState.fromCurrenciesList,
                        selectedCurrency = formUiState.fromCurrencySelected,
                        currencyAmount = formUiState.fromCurrencyAmount,
                        onCurrencySelected = {
                            onFormEvent(ConverterEvent.OnFromCurrencySelected(it))
                        },
                        onCurrencyAmountChanged = {
                            onFormEvent(ConverterEvent.OnFromCurrencyAmountSelected(it))
                        }
                    )

                    CurrencyField(
                        modifier = Modifier.padding(top = 8.dp),
                        currencies = formUiState.toCurrenciesList,
                        selectedCurrency = formUiState.toCurrencySelected,
                        currencyAmount = formUiState.toCurrencyAmount,
                        onCurrencySelected = {
                            onFormEvent(ConverterEvent.OnToCurrencySelected(it))
                        }
                    )
                }

                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primary,
                    border = BorderStroke(
                        width = 0.5.dp,
                        color = Color.LightGray
                    )
                ) {
                    Icon(
                        modifier = Modifier.padding(4.dp),
                        painter = painterResource(R.drawable.ic_arrow_downward),
                        contentDescription = null
                    )
                }
            }

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                when (converterState) {
                    ConverterState.Loading -> CircularProgressIndicator()
                    ConverterState.Success -> Text(
                        text = "Conversão realizado com sucesso!",
                        color = Color.Green
                    )
                    is ConverterState.Error -> Text(
                        text = converterState.message,
                        color = MaterialTheme.colorScheme.error
                    )
                    else -> Unit
                }
            }

            Button(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                contentPadding = PaddingValues(vertical = 16.dp),
                onClick = { onFormEvent(ConverterEvent.SendConverterForm) }
            ) {
                Text(text = "Converter Moeda")
            }
        }
    }
}

@Preview
@Composable
private fun ConverterContentPreview() {
    CurrencyConverterTheme {
        ConverterContent(
            formUiState = ConverterFormUiState(
                fromCurrenciesList = listOf("BRL", "USD", "EUR"),
                toCurrenciesList = listOf("USD", "EUR", "BRL"),
                fromCurrencySelected = "BRL",
                toCurrencySelected = "USD"
            ),
            converterState = ConverterState.Idle,
            onFormEvent = {}
        )
    }
}