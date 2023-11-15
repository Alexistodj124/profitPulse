package com.uvg.profitpulse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import java.time.LocalDate
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uvg.profitpulse.ui.theme.ProfitPulseTheme


class Expenses : ComponentActivity() {
    private val expensesList = mutableListOf<Expense>()
    private var totalSpent = 0.0
    private var expenseName by mutableStateOf("")
    private var expenseValue by mutableStateOf("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                ProfitPulseTheme {
                    ExpenseTracker(
                        expensesList = expensesList,
                        totalSpent = totalSpent,
                        expenseName = expenseName,
                        expenseValue = expenseValue,
                        onAddExpense = { expense ->
                            expensesList.add(expense)
                            totalSpent += expense.value

                            // Clear input fields
                            expenseName = ""
                            expenseValue = ""
                        },
                        onNameChange = { expenseName = it },
                        onValueChange = { expenseValue = it }
                    )
                }
            }
        }
    }

    @OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
    @Composable
    fun ExpenseTracker(
        expensesList: List<Expense>,
        totalSpent: Double,
        expenseName: String,
        expenseValue: String,
        onAddExpense: (Expense) -> Unit,
        onNameChange: (String) -> Unit,
        onValueChange: (String) -> Unit,
        modifier: Modifier = Modifier
    ) {
        Text(
            text = "Registro de gastos",
            color = Color.Black,
            style = TextStyle(fontSize = 27.sp),
            modifier = modifier
                .padding(start = 55.dp, top = 80.dp)
        )
        Box(
            modifier = modifier
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.flecha_correcta__1_),
                contentDescription = null,
                modifier = modifier
                    .offset(x = 17.dp, y = 45.dp)
                    .size(25.dp)
            )
        }

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Name input
            TextField(
                value = expenseName,
                onValueChange = { onNameChange(it) },
                label = { Text("Nombre del gasto") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            // Value input
            TextField(
                value = expenseValue,
                onValueChange = { onValueChange(it) },
                label = { Text("Valor del gasto") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            // Add Expense Button
            Button(
                onClick = {
                    if (expenseName.isNotEmpty() && expenseValue.isNotEmpty()) {
                        val value = expenseValue.toDouble()
                        onAddExpense(Expense(expenseName, value))
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Text("Agregar gasto")
            }

            // Expense List
            LazyColumn {
                items(expensesList) { expense ->
                    ExpenseItem(expense)
                }
            }

            // Total Spent
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Green)
                    .padding(8.dp)
            ) {
                Text("Total: $totalSpent", color = Color.White)
            }
        }
    }

    @Composable
    fun ExpenseItem(expense: Expense) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text("Name: ${expense.name}")
            Text("Value: ${expense.value}")
        }
    }

    data class Expense(val name: String, val value: Double)
}

@Preview
@Composable
fun GreetingPreview() {
    ProfitPulseTheme {
        ExpenseTracker(
            expensesList = emptyList(),
            totalSpent = 0.0,
            expenseName = "",
            expenseValue = "",
            onAddExpense = {},
            onNameChange = {},
            onValueChange = {}
        )
    }
}
