package com.example.prototipo_proyecto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uvg.profitpulse.ui.theme.ProfitPulseTheme



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun newAccount( modifier: Modifier = Modifier) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isChecked = remember { mutableStateOf(false) }
    Column(modifier.fillMaxSize()){
        Text(
            text = "Crear Cuenta",
            textAlign = TextAlign.Center,
            fontSize = 35.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = modifier
                .align(Alignment.Start)
                .padding(start = 20.dp, top = 60.dp)

        )
        Spacer(
            modifier = modifier
                .fillMaxWidth()
                .height(23.dp)
                .background(Color.Transparent)
        )
        TextField(
            value = username,
            label = {
                Text(
                    text = "Email",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(15, 223, 105)
                )
            },
            onValueChange = {username = it},
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
            value = password,
            label = {
                Text(
                    text = "Password",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(15, 223, 105)
                )
            },
            visualTransformation = PasswordVisualTransformation(),
            onValueChange = {password = it},
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

        Row{
            Checkbox(
                checked = isChecked.value,
                onCheckedChange = {  },
                modifier = modifier
                    .padding(start = 50.dp)
            )
            Text(
                text="Acepto términos y condiciones",
                fontSize = 16.sp,
                color = Color(156,156,156),
                modifier = modifier
                    .padding(top = 12.dp)
            )
        }

        Button(onClick ={ },
            modifier = modifier
                .align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color (32, 193, 102)
            )
        ){
            Text(text = "Crear cuenta",
                fontSize = 20.sp
            )
        }
        Text (
            text = "¿Tienes cuenta? Inicia sesión",
            color = Color(151,151,151),
            fontSize = 18.sp,
            modifier = modifier
                .padding(top = 20.dp)
                .align(Alignment.CenterHorizontally)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ProfitPulseTheme {
        newAccount()
    }
}