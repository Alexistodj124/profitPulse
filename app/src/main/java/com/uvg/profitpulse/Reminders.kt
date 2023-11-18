package com.uvg.profitpulse

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
import com.uvg.profitpulse.ui.theme.ProfitPulseTheme
import java.util.Date

class Reminders : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProfitPulseTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    reminders()
                }
            }
        }
    }
}
data class Reminder(val description: String, val date: Date)
@Composable
fun reminders(){
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
        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()

                .verticalScroll(rememberScrollState())

        ) {
            val reminders = remember {
                mutableStateListOf(
                    Reminder("Hacer Publicacion Chapstick (Instagram/Facebook)", Date(2023, 9, 20)),
                    Reminder("Hacer TikTok Primer", Date(2023, 9, 21)),
                    Reminder("Hacer Reel Piel Mixta", Date(2023, 9, 22)),
                    Reminder("Hacer Publicacion Chapstick (Instagram/Facebook)", Date(2023, 9, 20)),
                    Reminder("Hacer TikTok Primer", Date(2023, 9, 21)),
                    Reminder("Hacer Reel Piel Mixta", Date(2023, 9, 22)),
                    Reminder("Hacer Publicacion Chapstick (Instagram/Facebook)", Date(2023, 9, 20)),
                    Reminder("Hacer TikTok Primer", Date(2023, 9, 21)),
                    Reminder("Hacer Reel Piel Mixta", Date(2023, 9, 22)),
                    Reminder("Hacer Publicacion Chapstick (Instagram/Facebook)", Date(2023, 9, 20)),
                    Reminder("Hacer TikTok Primer", Date(2023, 9, 21)),
                    Reminder("Hacer Reel Piel Mixta", Date(2023, 9, 22)),
                    Reminder("Hacer Publicacion Chapstick (Instagram/Facebook)", Date(2023, 9, 20)),
                    Reminder("Hacer TikTok Primer", Date(2023, 9, 21)),
                    Reminder("Hacer Reel Piel Mixta", Date(2023, 9, 22)),
                )
            }
            for (reminder in reminders.toList()) {
                Card (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .background(Color.White),
                    shape = MaterialTheme.shapes.medium,
                ){
                    ReminderRow(reminder = reminder) { isChecked ->
                        if (isChecked) {
                            reminders.remove(reminder)
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
fun ReminderRow(reminder: Reminder, onCheckedChange: (Boolean) -> Unit) {
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
                text = reminder.description,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = "Date:" + reminder.date,
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