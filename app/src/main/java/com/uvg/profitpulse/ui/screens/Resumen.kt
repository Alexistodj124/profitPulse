package com.uvg.profitpulse.ui.screens


import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uvg.profitpulse.R
import com.uvg.profitpulse.ui.theme.ProfitPulseTheme
import com.uvg.profitpulse.utils.AuthManager
import com.uvg.profitpulse.utils.RealtimeManager
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.uvg.profitpulse.Model.Gastos
import com.uvg.profitpulse.Model.Ventas

class Resumen : ComponentActivity() {
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
                    Summary(realtimeManager = realtimeManager, authManager = authManager)
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Summary( modifier: Modifier = Modifier,realtimeManager: RealtimeManager, authManager: AuthManager) {
    var showV by remember { mutableStateOf(true) }
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
        Image(
            painter = painterResource(id = R.drawable.flecha_correcta__2_),
            contentDescription = null,
            modifier = modifier
                .padding(top = 30.dp, start = 25.dp)
                .size(30.dp)
        )
        Text(
            text = stringResource(R.string.Summary),
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
            text = stringResource(R.string.Performance_sales),
            textAlign = TextAlign.Right,
            fontSize = 16.sp,
            modifier = modifier
                .align(Alignment.End)
                .padding(end = 20.dp)
        )

        Spacer(
            modifier = modifier
                .fillMaxWidth()
                .height(20.dp)
                .background(Color.Transparent)
        )

        Divider(
            modifier = Modifier
                .height(5.dp)
                .background(Color.Gray)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .wrapContentSize(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val metaVentas: Double = 10000.00
                Text("Meta de Ventas",
                    fontSize = 15.sp,
                    modifier = Modifier.padding(bottom = 5.dp))
                CircularProgressBar(percentage = (totalVentasMonto*1)/(metaVentas))
            }

            Spacer(modifier = Modifier.width(16.dp)) // Add spacing between the circles

            Column(
                modifier = Modifier
                    .weight(1f)
                    .wrapContentSize(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val maximoGastos: Double = 3000.00
                Text("Maximo de Gastos",
                    fontSize = 15.sp,
                    modifier = Modifier.padding(bottom = 5.dp))
                CircularProgressBar(percentage = (totalGastoMonto*1)/(maximoGastos))
            }
        }

        Spacer(
            modifier = modifier
                .fillMaxWidth()
                .height(20.dp)
                .background(Color.Transparent)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { showV = true },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color (32, 193, 102)
                )
            ) {
                Text(
                    text = "Ventas",
                    color = Color.Black
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                onClick = { showV = false },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color (32, 193, 102)
                )
            ) {
                Text(
                    text = "Gastos",
                    color = Color.Black
                )
            }
            
        }
        Spacer(
            modifier = modifier
                .fillMaxWidth()
                .height(10.dp)
                .background(Color.Transparent)
        )
        Divider(
            modifier = Modifier
                .height(5.dp)
                .background(Color.Gray)
        )
        Spacer(
            modifier = modifier
                .fillMaxWidth()
                .height(10.dp)
                .background(Color.Transparent)
        )
        if (showV == true){
            showVentas(realtimeManager = realtimeManager)
        }else{
            showGastos(realtimeManager = realtimeManager)
        }
    }
}
@Composable
fun CircularProgressBar(percentage: Double, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(100.dp)
            .background(Color.Transparent, shape = MaterialTheme.shapes.small)
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            val center = Offset(canvasWidth / 2, canvasHeight / 2)
            val radius = size.width / 2

            drawCircle(
                color = Color.Gray,
                center = center,
                radius = radius,
                style = Stroke(5.dp.toPx())
            )

            drawArc(
                color = Color (32, 193, 102),
                startAngle = -90f,
                sweepAngle = (360 * percentage).toFloat(),
                useCenter = false,
                style = Stroke(8.dp.toPx())
            )
        }

        Text(
            text = "${(percentage * 100).toInt()}%",
            modifier = Modifier
                .align(Alignment.Center)
                .padding(top = 5.dp),
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}
@Composable
fun VentaRow( venta: Ventas, realtimeManager: RealtimeManager) {
    Log.d("TotalGastos", "Nuevo Reminder")
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = venta.ventasDescripcion)
        Spacer(modifier = Modifier.width(16.dp)) // Adjust spacing as needed
        Text(text = "Q " + venta.ventasMonto)
    }
}
@Composable
fun GastoRow( gasto: Gastos, realtimeManager: RealtimeManager) {
    Log.d("TotalGastos", "Nuevo Reminder")
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = gasto.gastoDescripcion)
        Spacer(modifier = Modifier.width(16.dp)) // Adjust spacing as needed
        Text(text = "Q " + gasto.gastoMonto)
    }
}
@Composable
fun showVentas(realtimeManager: RealtimeManager,){
    val ventas: List<Ventas> = realtimeManager.getVentas().collectAsState(emptyList()).value
    LazyColumn(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()


    ) {


        items(ventas) { venta ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(Color.White),
                shape = MaterialTheme.shapes.medium,
            ) {

                VentaRow(venta = venta, realtimeManager = realtimeManager)
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
@Composable
fun showGastos(realtimeManager: RealtimeManager,){
    val gastos: List<Gastos> = realtimeManager.getGasto().collectAsState(emptyList()).value
    LazyColumn(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()


    ) {


        items(gastos) { gasto ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(Color.White),
                shape = MaterialTheme.shapes.medium,
            ) {

                GastoRow(gasto = gasto, realtimeManager = realtimeManager)
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SummaryPreview() {
    ProfitPulseTheme {
    }
}
