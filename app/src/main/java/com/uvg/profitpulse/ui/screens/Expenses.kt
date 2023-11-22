package com.uvg.profitpulse.ui.screens

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uvg.profitpulse.Model.Gastos
import com.uvg.profitpulse.R
import com.uvg.profitpulse.ui.theme.ProfitPulseTheme
import com.uvg.profitpulse.utils.AuthManager
import com.uvg.profitpulse.utils.RealtimeManager


class Expenses : ComponentActivity() {
    private lateinit var realtimeManager: RealtimeManager
    private lateinit var authManager: AuthManager
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        realtimeManager = RealtimeManager(getApplicationContext())
        authManager = AuthManager()
        setContent {
            ProfitPulseTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ExpenseTracker(realtimeManager = realtimeManager, authManager = authManager)
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun ExpenseTracker(modifier: Modifier = Modifier, realtimeManager: RealtimeManager, authManager: AuthManager) {
    var description by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf(0.0) }
    var date by remember { mutableStateOf("") }



    Column(modifier.fillMaxSize()){
        Image(
            painter = painterResource(id = R.drawable.flecha_correcta__2_),
            contentDescription = null,
            modifier = modifier
                .padding(top = 30.dp, start = 25.dp)
                .size(30.dp)
        )
        Text(
            text = "Registro de gastos",
            textAlign = TextAlign.Center,
            fontSize = 30.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = modifier
                .align(Alignment.Start)
                .padding(start = 35.dp, top = 25.dp)
        )
        Spacer(
            modifier = modifier
                .fillMaxWidth()
                .height(15.dp)
                .background(Color.Transparent)
        )

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = {
                Text(
                    "Descripción",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .background(Color.Transparent)
        )
        OutlinedTextField(
            value = amount.toString(),
            onValueChange = { amount = (it.toDoubleOrNull()?:0) as Double },
            label = {
                Text(
                    "Valor",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                ) },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .background(Color.Transparent)
        )
        OutlinedTextField(
            value = date,
            onValueChange = { date = it },
            label = {
                Text(
                    "Fecha",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                ) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )
        Spacer(
            modifier = modifier
                .fillMaxWidth()
                .height(15.dp)
                .background(Color.Transparent)
        )
        Button(
            onClick ={
                val expense = Gastos(
                    gastoMonto = amount,
                    gastoDescripcion = description,
                    gastoDate = date,
                    uid = authManager.getCurrentUser()?.uid.toString()
                )
                realtimeManager.addGasto(expense)
                description = "" //clean fields
                amount = 0.0
                date = ""
            },
            modifier = modifier
                .align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color (32, 193, 102)
            )
        ){
            Text(text = "Agregar gasto",
                fontSize = 25.sp)
        }
        val expenses: List<Gastos> = realtimeManager.getGasto().collectAsState(emptyList()).value
        if (expenses.isEmpty()) {
            Log.d("TotalGastos", "La lista de gastos está vacía")
        }
        val totalGastoMonto: Double = expenses.sumOf { it.gastoMonto }
        Spacer(
            modifier = modifier
                .fillMaxWidth()
                .height(23.dp)
                .background(Color.Transparent)
        )

        Text(
            "TOTAL GASTOS Q $totalGastoMonto",
            fontSize = 20.sp,
            modifier = modifier
                .padding(top = 40.dp)
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center)
        )




    }
}

fun SumaGastos(expenses: Gastos): Double {
    Log.d("Errorrr hueco", "HUECOOOOOOOOOOOO")
    return expenses.gastoMonto
}


@Preview(showBackground = true)
@Composable
fun ExpensePreview() {
    ProfitPulseTheme {
    }
}
