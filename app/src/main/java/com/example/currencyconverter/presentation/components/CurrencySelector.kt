package com.example.currencyconverter.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.currencyconverter.R
import com.example.currencyconverter.presentation.theme.CurrencyConverterTheme

@Composable
fun CurrencySelector(
    modifier: Modifier = Modifier,
    currencies: List<String>,
    selectedCurrency: String,
    onCurrencySelected: (String) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = modifier
            .clickable { expanded = true }
            .padding(16.dp),
    ) {
        Text(
            text = selectedCurrency,
            fontWeight = FontWeight.Bold
        )

        Icon(
            modifier = Modifier.padding(start = 8.dp),
            painter = painterResource(R.drawable.ic_arrow_drop_down),
            contentDescription = null
        )
    }

    Box {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            currencies.forEach { currency ->
                DropdownMenuItem(
                    text = { Text(text = currency) },
                    onClick = {
                        onCurrencySelected(currency)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CurrencySelectorPreview() {
    CurrencyConverterTheme {
        CurrencySelector(
            currencies = listOf("USD", "EUR", "BRL"),
            selectedCurrency = "USD",
            onCurrencySelected = {
                // Handle currency selection
            }
        )
    }
}