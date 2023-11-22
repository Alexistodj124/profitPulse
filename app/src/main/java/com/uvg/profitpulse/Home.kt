    package com.uvg.profitpulse
    
    import android.os.Bundle
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
    import androidx.compose.foundation.layout.padding
    import androidx.compose.foundation.layout.size
    import androidx.compose.foundation.layout.width
    import androidx.compose.foundation.lazy.LazyColumn
    import androidx.compose.foundation.lazy.LazyRow
    import androidx.compose.foundation.lazy.items
    import androidx.compose.material3.Button
    import androidx.compose.material3.ButtonDefaults
    import androidx.compose.material3.Card
    import androidx.compose.material3.MaterialTheme
    import androidx.compose.material3.Surface
    import androidx.compose.material3.Text
    import androidx.compose.runtime.Composable
    import androidx.compose.runtime.remember
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.graphics.Color
    import androidx.compose.ui.res.painterResource
    import androidx.compose.ui.res.stringResource
    import androidx.compose.ui.text.style.TextAlign
    import androidx.compose.ui.tooling.preview.Preview
    import androidx.compose.ui.unit.dp
    import androidx.compose.ui.unit.sp
    import com.uvg.profitpulse.ui.theme.ProfitPulseTheme
    
    class Home : ComponentActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContent {
                ProfitPulseTheme {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        HomeScreen()
                    }
                }
            }
        }
    }
    
    data class CardItem(val id: Int, val title: String)
    
    @Composable
    fun HomeScreen(modifier: Modifier    = Modifier) {
        val cardList = remember {
            (1..4).map { CardItem(it, "Tarjeta $it") }
        }
        val userImage = R.drawable.user_profile_pic
        val menuItems = listOf(
            R.string.Expenses,
            R.string.Sales,
            R.string.Revenue,
            R.string.Summary,
            R.string.Reminders
        )
        Column(modifier.fillMaxSize()){
            Row( modifier = Modifier
                .padding(5.dp)){
                Image(
                    painter = painterResource(id = userImage),
                    contentDescription = "App User Icon",
                    modifier = modifier
                        .size(70.dp)
                        .padding(top = 10.dp,
                            end = 10.dp,
                            start = 10.dp)
                )
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
                items(cardList){card ->
                    Card( modifier = Modifier
                        .width(350.dp)
                        .height(200.dp)
                        .padding(start = 49.dp, end = 10.dp)
                        .align(Alignment.CenterHorizontally)
                    ){
                        Text(
                            text = "Resumen de ganancias mensuales",
                            fontSize = 19.sp,
                            modifier = modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(top = 15.dp),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "Q. 22000",
                            fontSize = 18.sp,
                            modifier = modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(top = 10.dp)
                        )
                    }
                }
            }
    
           LazyColumn(
               modifier = modifier
                   .fillMaxWidth()
                   .padding(horizontal = 16.dp)
           ){
               item{
                   Text(
                       text = stringResource(R.string.Menu_Title),
                       fontSize = 20.sp,
                       modifier = modifier
                           .padding(top = 15.dp)
                   )
               }
    
               items(menuItems){ menuItem ->
                   Card(
                       modifier = Modifier
                           .fillMaxWidth()
                           .padding(vertical = 9.dp,)
                           .clickable {
    
                           }
                   ){
                        Text(
                            text = stringResource(menuItem),
                            fontSize = 18.sp,
                            modifier = modifier
                                .padding(16.dp)
                                .align(Alignment.CenterHorizontally)
                        )
                   }
               }
           }
        }
    }
    
    @Preview(showBackground = true)
    @Composable
    fun HomeScreenPreview() {
        ProfitPulseTheme {
            HomeScreen()
        }
    }