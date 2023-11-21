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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uvg.profitpulse.R
import com.uvg.profitpulse.ui.theme.ProfitPulseTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Summary( modifier: Modifier = Modifier) {
    var username by remember { mutableStateOf("") }
    Column(modifier.fillMaxSize()){
        Image(
            painter = painterResource(id = R.drawable.flecha_correcta__2_),
            contentDescription = null,
            modifier = modifier
                .padding(top = 30.dp, start = 25.dp)
                .size(30.dp)
        )
        Text(
            text = "Resumen",
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
                .height(10.dp)
                .background(Color.Transparent)
        )
        Text(
            text = "Rendimiento de ventas",
            textAlign = TextAlign.Right,
            fontSize = 16.sp,
            modifier = modifier
                .align(Alignment.Start)
                .padding(start = 190.dp)
        )

        Spacer(
            modifier = modifier
                .fillMaxWidth()
                .height(35.dp)
                .background(Color.Transparent)
        )

        Spacer(
            modifier = modifier
                .fillMaxWidth()
                .height(29.dp)
                .background(Color.Transparent)
        )




    }
}

@Preview(showBackground = true)
@Composable
fun SummaryPreview() {
    ProfitPulseTheme {
        Summary()
    }
}