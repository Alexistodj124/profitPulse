package com.uvg.profitpulse.ui.screens

import android.os.Bundle
import android.util.Log
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
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.play.integrity.internal.t
import com.uvg.profitpulse.Model.Gastos
import com.uvg.profitpulse.Model.Ventas
import com.uvg.profitpulse.R
import com.uvg.profitpulse.ui.theme.ProfitPulseTheme
import com.uvg.profitpulse.utils.AuthManager
import com.uvg.profitpulse.utils.RealtimeManager

class Sales : ComponentActivity() {
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
                    SalesTracker(realtimeManager = realtimeManager, authManager = authManager)
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SalesTracker(modifier: Modifier = Modifier,realtimeManager: RealtimeManager, authManager: AuthManager) {
    var name by remember { mutableStateOf("") }
    var product by remember { mutableStateOf("") }
    var total by remember { mutableStateOf(0.0) }

    Column(modifier.fillMaxSize()){
        Image(
            painter = painterResource(id = R.drawable.flecha_correcta__2_),
            contentDescription = null,
            modifier = modifier
                .padding(top = 30.dp, start = 25.dp)
                .size(30.dp)
        )
        Text(
            text = stringResource(R.string.Sales_registry),
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
            value = name,
            onValueChange = { name = it },
            label = {
                Text(stringResource(R.string.Sales_client))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .background(Color.Transparent)
        )
        OutlinedTextField(
            value = product,
            onValueChange = { product = it },
            label = { Text(stringResource(R.string.Sale_product)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .background(Color.Transparent)
        )
        OutlinedTextField(
            value = total.toString(),
            onValueChange = { total = (it.toDoubleOrNull()?:0) as Double },
            label = { Text(stringResource(R.string.Total)) },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .background(Color.Transparent)
        )
        Spacer(
            modifier = modifier
                .fillMaxWidth()
                .height(15.dp)
                .background(Color.Transparent)
        )
        Button(
            onClick = {
                val venta = Ventas(
                    ventasCliente = name,
                    ventasDescripcion = product,
                    ventasMonto = total,
                    uid = authManager.getCurrentUser()?.uid.toString()
                )
                realtimeManager.addVenta(venta)
                name = ""
                product = ""
                total = 0.0
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color (32, 193, 102)
            )
        ) {
            Text(
                stringResource(R.string.Register_sale),
                fontSize = 28.sp
            )
        }
        Spacer(
            modifier = modifier
                .fillMaxWidth()
                .height(23.dp)
                .background(Color.Transparent)
        )
        val ventas: List<Ventas> = realtimeManager.getVentas().collectAsState(emptyList()).value
        if (ventas.isEmpty()) {
            Log.d("TotalGastos", "La lista de gastos está vacía")
        }
        val totalVentasMonto: Double = ventas.sumOf { it.ventasMonto }
        Text(
            "TOTAL VENTAS Q $totalVentasMonto",
            fontSize = 20.sp,
            modifier = modifier
                .padding(top = 40.dp)
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun SalePreview() {
    ProfitPulseTheme {
    }
}