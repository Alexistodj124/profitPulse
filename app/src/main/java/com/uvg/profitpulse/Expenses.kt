package com.example.prototipo_proyecto

import android.os.Bundle
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uvg.profitpulse.R
import com.uvg.profitpulse.ui.theme.ProfitPulseTheme



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun expenseTracker( modifier: Modifier = Modifier) {
    var expenseName by remember { mutableStateOf("") }
    var expenseValue by remember { mutableStateOf("") }
    Column{
        Text(
            text = "Registro de gastos",
            style = TextStyle(fontSize = 18.sp),
            fontWeight = FontWeight.Bold,
            modifier = modifier
                .fillMaxSize()
                .padding(28.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.flecha_correcta__1_),
            contentDescription = null,
            modifier = modifier
                .padding(top = 3.dp, start = 25.dp)
                .size(25.dp)
        )
        TextField(
            value = expenseName,
            label = {
                Text(
                    text = "Nombre del gasto",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(15, 223, 105)
                )
            },
            onValueChange = {expenseName = it},
            modifier = modifier
                .align(Alignment.CenterHorizontally),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
            )
        )
        Spacer(
            modifier = modifier
                .fillMaxWidth()
                .height(35.dp)
                .background(Color.Transparent)
        )
        TextField(
            value = expenseValue,
            label = {
                Text(
                    text = "Valor del gasto",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(15, 223, 105)
                )
            },
            onValueChange = {expenseValue = it},
            modifier = modifier
                .align(Alignment.CenterHorizontally),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent
            )
        )
        Spacer(
            modifier = modifier
                .fillMaxWidth()
                .height(29.dp)
                .background(Color.Transparent)
        )



        Button(onClick ={},
            modifier = modifier
                .align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color (32, 193, 102)
            )
        ){
            Text(text = "Agregar gasto",
                fontSize = 20.sp
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun TrackerPreview() {
    ProfitPulseTheme {
        expenseTracker()
    }
}