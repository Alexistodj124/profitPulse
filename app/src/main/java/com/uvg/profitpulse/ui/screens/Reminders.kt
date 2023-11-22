package com.uvg.profitpulse.ui.screens

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uvg.profitpulse.Model.Recordatorios
import com.uvg.profitpulse.R
import com.uvg.profitpulse.ui.theme.ProfitPulseTheme
import com.uvg.profitpulse.utils.AuthManager
import com.uvg.profitpulse.utils.RealtimeManager
import java.util.Date


class Reminders : ComponentActivity() {
    private lateinit var realtimeManager: RealtimeManager
    private lateinit var authManager: AuthManager
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            ProfitPulseTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    reminders(realtimeManager = realtimeManager, authManager = authManager)
                }
            }
        }
    }
}

@Composable
fun reminders(realtimeManager: RealtimeManager, authManager: AuthManager){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val reco = Recordatorios(
            recordatorioDate = Date(2023,9,21),
            recordatorioDescripcion = "HOLA COMO ESTAS",
            uid = authManager.getCurrentUser()?.uid.toString()
        )
        realtimeManager.addRemidner(reco)
        Text(
            "Recordatorios",
            textAlign = TextAlign.Center,
            fontSize = 40.sp
        )
        Image(
            painter = painterResource(id = R.drawable.reminder),
            contentDescription = ""
        )
        val recordatorios by realtimeManager.getReminder()
            .collectAsState(emptyList())
        LazyColumn(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()

                .verticalScroll(rememberScrollState())

        ) {

            items(recordatorios) { recordatorio ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .background(Color.White),
                    shape = MaterialTheme.shapes.medium,
                ) {

                    ReminderRow(reminder = recordatorio, realtimeManager = realtimeManager) { isChecked ->
                        if (isChecked) {
                            // Do something when the reminder is checked
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
    CircularButtonWithPlusSign()
}


@Composable
fun CircularButtonWithPlusSign() {
    var text by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        FloatingActionButton(
            onClick = { },
            modifier = Modifier
                .size(75.dp)
                .background(color = MaterialTheme.colorScheme.primary, shape = CircleShape),
        ) {
            Icon(

                imageVector = Icons.Default.Add,
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}

@Composable
fun ReminderRow(reminder: Recordatorios,realtimeManager: RealtimeManager, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(135, 240, 145))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        var checkedState by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = reminder.recordatorioDescripcion,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = "Date:" + reminder.recordatorioDate,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.primary
            )
        }
        Checkbox(
            checked = checkedState,
            onCheckedChange = { isChecked ->
                checkedState = isChecked
                onCheckedChange(isChecked)
            }
        )


    }
}