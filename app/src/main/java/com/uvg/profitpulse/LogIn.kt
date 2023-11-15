package com.uvg.profitpulse

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.uvg.profitpulse.ui.theme.ProfitPulseTheme

class LogIn : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        auth = Firebase.auth
        super.onCreate(savedInstanceState)
        setContent {
            ProfitPulseTheme {
                // A surface container using the 'background' color from the theme
                Prototype(
                    onLoginClick = { email, password ->
                        login(email, password)
                    },
                    onSignUpnClick = {
                        startActivity(Intent(this, MainActivity::class.java))
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            reload()
        }
    }
    private fun reload() {
        auth.currentUser!!.reload().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Reload successful! Hello ${auth.currentUser?.uid}", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Log.e(TAG, "reload", task.exception)
                Toast.makeText(this, "Failed to reload user.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    // val user = auth.currentUser
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }
    companion object {
        private const val TAG = "LoginActivity"
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Prototype(
    onLoginClick: (email: String, password: String) -> Unit,
    onSignUpnClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val imageLogin = R.drawable.loginicon
    Column(modifier.fillMaxSize()){
        Text(
            text = "ProfitPulse",
            textAlign = TextAlign.Center,
            fontSize = 40.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 30.dp)
                .offset(y = 25.dp)
        )
        Text(
            text = "ProfitPulse",
            textAlign = TextAlign.Center,
            fontSize = 40.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 30.dp)
                .offset(y = 25.dp)
        )
        Image(
            painter = painterResource(id = imageLogin),
            contentDescription = "Profit Pulse Icon",
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .size(120.dp)
        )
        Text(
            text = "Iniciar Sesión",
            fontSize = 23.sp,
            fontWeight = FontWeight.Medium,
            modifier = modifier
                .align(Alignment.Start)
                .padding(start = 20.dp, top = 10.dp)
        )
        Text(
            text = "¡Hola! Que gusto verte de nuevo",
            fontSize = 17.sp,
            modifier = modifier
                .padding (start = 26.dp, bottom = 10.dp),
            color = Color.Gray
        )
        TextField(
            value = email,
            label = {
                Text(
                    text = "Email",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(15, 223, 105)
                )
            },
            onValueChange = {email = it},
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
                .height(33.dp)
                .background(Color.Transparent)
        )
        Button(onClick ={ onLoginClick(email, password) },
            modifier = modifier
                .align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color (32, 193, 102)
            )
        ){
            Text(text = "Iniciar Sesión",
                fontSize = 20.sp)
        }
        Button(onClick ={ onSignUpnClick() },
            modifier = modifier
                .align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color (32, 193, 102)
            )
        ){
            Text(text = "Crear Cuenta",
                fontSize = 20.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PrototypePreview() {
    ProfitPulseTheme {
        Prototype(
            onLoginClick = { _, _ ->

            },
            onSignUpnClick = {

            },
            modifier = Modifier.fillMaxSize()
        )
    }
}