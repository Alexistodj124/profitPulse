package com.uvg.profitpulse

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.util.Date

data class Reminder(val description: String, val date: Date)
@Composable
fun reminders(){
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Recordatorios", modifier = Modifier.padding(start = 25.dp))
        Column(
            modifier = Modifier
                .background(Color.LightGray)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ){
            val reminders = listOf(
                Reminder("alexis", Date(2023,2,20)),
                Reminder("isa", Date(2023,2,20)),
                Reminder("kelson", Date(2023,2,20)),
            )
            Text(text = "Recordatorios", modifier = Modifier.padding(start = 25.dp))





        }

    }
}