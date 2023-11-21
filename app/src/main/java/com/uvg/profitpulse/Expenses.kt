package com.example.prototipo_proyecto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.Calendar
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uvg.profitpulse.R
import com.uvg.profitpulse.ui.theme.ProfitPulseTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun ExpenseTracker(modifier: Modifier = Modifier) {
    var description by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var expenses by remember { mutableStateOf(listOf<Double>()) }

    fun calculateTotal(): Double {
        return expenses.sum()
    }

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
            value = amount,
            onValueChange = { amount = it },
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
                expenses = expenses + (amount.toDoubleOrNull() ?: 0.0)
                description = "" //clean fields
                amount = ""
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
        Spacer(
            modifier = modifier
                .fillMaxWidth()
                .height(23.dp)
                .background(Color.Transparent)
        )
        Text(
            text = "Total",
            textAlign = TextAlign.Center,
            fontSize = 25.sp,
            color = Color(15, 223, 105),
            fontWeight = FontWeight.SemiBold,
            modifier = modifier
                .align(Alignment.Start)
                .padding(start = 35.dp)
        )
        Text(
            "Total de gastos: ${calculateTotal()}",
            fontSize = 20.sp,
            modifier = modifier
                .padding(start = 120.dp, top = 40.dp)
        )



    }
}


@Preview(showBackground = true)
@Composable
fun ExpensePreview() {
    ProfitPulseTheme {
        ExpenseTracker()
    }
}