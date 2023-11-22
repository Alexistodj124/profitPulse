package com.uvg.profitpulse.ui.screens

import android.os.Bundle
import android.util.Log
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
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
        realtimeManager = RealtimeManager(getApplicationContext())
        authManager = AuthManager()
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
        if (recordatorios.isEmpty()) {
            Log.d("TotalGastos", "La lista de recordatorios está vacía")
        }
        LazyColumn(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()


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

                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
    CircularButtonWithPlusSign(realtimeManager = realtimeManager, authManager = authManager)
}


@Composable
fun CircularButtonWithPlusSign(realtimeManager: RealtimeManager, authManager: AuthManager) {
    var text by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        var shownewreminder by remember { mutableStateOf(false) }
        var textValue by remember { mutableStateOf("") }
        FloatingActionButton(
            onClick = { shownewreminder = true},
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
        if (shownewreminder) {
            newReminder(
                onDismiss = { shownewreminder = false },
                onConfirm = {
                    val reco = Recordatorios(
                        recordatorioDate = Date(2023,9,21),
                        recordatorioDescripcion = textValue,
                        uid = authManager.getCurrentUser()?.uid.toString()
                    )
                    realtimeManager.addRemidner(reco)
                    shownewreminder = false
                },
                textValue = textValue,
                onTextValueChange = { textValue = it }
            )
        }
    }
}

@Composable
fun ReminderRow(reminder: Recordatorios,realtimeManager: RealtimeManager, onCheckedChange: (Boolean) -> Unit) {
    Log.d("TotalGastos", "Nuevo Reminder")
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
        }
        Checkbox(
            checked = checkedState,
            onCheckedChange = { isChecked ->
                checkedState = isChecked
                onCheckedChange(isChecked)
                if (isChecked) {
                    // Do something when checked
                    realtimeManager.deleteReminder(reminder.key ?: "")
                }
            }
        )


    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun newReminder(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    textValue: String,
    onTextValueChange: (String) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Ingrese un nuevo recordatorio") },
        text = {
            Column {
                TextField(
                    value = textValue,
                    onValueChange = { onTextValueChange(it) },
                    label = { Text("Text") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Text
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm()
                    onDismiss()
                }
            ) {
                Text("Submit")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}