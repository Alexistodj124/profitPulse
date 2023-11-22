package com.uvg.profitpulse.ui.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import com.google.firebase.auth.FirebaseUser
import com.uvg.profitpulse.ui.screens.HomeScreen
import com.uvg.profitpulse.utils.AuthManager

sealed class Routes(val route: String) {
    object Login : Routes("Login Screen")
    object Home : Routes("Home Screen")
    object SignUp : Routes("SignUp Screen")
    object Recordatorios : Routes("Recordatorios")
}