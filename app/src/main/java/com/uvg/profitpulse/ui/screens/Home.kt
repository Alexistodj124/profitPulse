package com.uvg.profitpulse.ui.screens

import android.content.Intent
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.core.Context
import com.uvg.profitpulse.Model.Gastos
import com.uvg.profitpulse.Model.Ventas
import com.uvg.profitpulse.R
import com.uvg.profitpulse.ui.theme.ProfitPulseTheme
import com.uvg.profitpulse.utils.AuthManager
import com.uvg.profitpulse.utils.RealtimeManager

class Home : ComponentActivity() {
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
                    HomeScreen(realtimeManager = realtimeManager, authManager = authManager)
                }
            }
        }
    }
}

data class CardItem(val id: Int, val title: String)

@Composable
fun HomeScreen(modifier: Modifier    = Modifier,realtimeManager: RealtimeManager, authManager: AuthManager) {
    val user = authManager.getCurrentUser()
    val mContext = LocalContext.current
    val userImage = R.drawable.user_profile_pic

    //FIREBASE
    //VENTAS
    val ventas: List<Ventas> = realtimeManager.getVentas().collectAsState(emptyList()).value
    if (ventas.isEmpty()) {
        Log.d("TotalGastos", "La lista de gastos está vacía")
    }
    val totalVentasMonto: Double = ventas.sumOf { it.ventasMonto }
    //GASTOS
    val expenses: List<Gastos> = realtimeManager.getGasto().collectAsState(emptyList()).value
    if (expenses.isEmpty()) {
        Log.d("TotalGastos", "La lista de gastos está vacía")
    }
    val totalGastoMonto: Double = expenses.sumOf { it.gastoMonto }

    Column(modifier.fillMaxSize()){
        Row( modifier = Modifier
            .padding(5.dp)){
            Image(
                painter = painterResource(id = userImage),
                contentDescription = "App User Icon",
                modifier = modifier
                    .size(70.dp)
                    .padding(
                        top = 10.dp,
                        end = 10.dp,
                        start = 10.dp
                    )
            )
            SignOutButton {
                // Handle sign-out logic
                authManager.signOut()
                mContext.startActivity(Intent(mContext, LogIn::class.java))
            }

            Text(
                text = stringResource(R.string.HomeGreeting),
                fontSize = 25.sp,
                modifier = modifier
                    .padding(8.dp)
                    .align(Alignment.CenterVertically)
            )
        }
        Spacer(
            modifier = modifier
                .fillMaxWidth()
                .height(10.dp)
                .background(Color.Transparent)
        )
        LazyRow(){
            item {
                Card( modifier = Modifier
                    .width(350.dp)
                    .height(200.dp)
                    .padding(start = 49.dp, end = 10.dp)
                    .align(Alignment.CenterHorizontally)
                ){
                    Text(
                        text = "Resumen de Ventas Totales",
                        fontSize = 19.sp,
                        modifier = modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 15.dp),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Q $totalVentasMonto" ,
                        fontSize = 18.sp,
                        modifier = modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 10.dp)
                    )
                }
                Card( modifier = Modifier
                    .width(350.dp)
                    .height(200.dp)
                    .padding(start = 49.dp, end = 10.dp)
                    .align(Alignment.CenterHorizontally)
                ){
                    Text(
                        text = "Resumen de Gastos Totales",
                        fontSize = 19.sp,
                        modifier = modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 15.dp),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Q $totalGastoMonto" ,
                        fontSize = 18.sp,
                        modifier = modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 10.dp)
                    )
                }
            }
        }
        Text(
            text = "Menu",
            fontSize = 20.sp,
            modifier = modifier
                .padding(start = 40.dp, top = 10.dp)
        )
        Button(onClick = {
            mContext.startActivity(Intent(mContext, Expenses::class.java))
        },
        modifier = modifier
            .align(Alignment.CenterHorizontally)
            .fillMaxWidth()
            .padding(top = 15.dp, start = 40.dp, end = 40.dp),
        colors = ButtonDefaults.buttonColors(
        containerColor = Color (65, 195, 121))
        ){
            Text(
                text = "Registro de gastos"
            )
        }
        Button(onClick = {
            mContext.startActivity(Intent(mContext, Sales::class.java))
        },
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .padding(top = 5.dp, start = 40.dp, end = 40.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color (65, 195, 121))
        ){
            Text(
                text = "Registro de ventas"
            )
        }
        Button(onClick = {
            mContext.startActivity(Intent(mContext, Resumen::class.java))
        },
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .padding(top = 5.dp, start = 40.dp, end = 40.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color (65, 195, 121))
        ){
            Text(
                text = "Resumen"
            )
        }
        Button(onClick = {
            mContext.startActivity(Intent(mContext, Reminders::class.java))
        },
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .padding(top = 5.dp, start = 40.dp, end = 40.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color (65, 195, 121))
        ){
            Text(
                text = "Recordatorios"
            )
        }

    }
}
@Composable
fun SignOutButton(onSignOutClick: () -> Unit) {
    Button(
        onClick = onSignOutClick,
        modifier = Modifier
            .padding(vertical = 8.dp),
                colors = ButtonDefaults.buttonColors(
                containerColor = Color (65, 195, 121))
    ) {
        Text("Sign Out")
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    ProfitPulseTheme {
    }
}