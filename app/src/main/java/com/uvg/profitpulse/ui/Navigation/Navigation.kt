package com.uvg.profitpulse.ui.Navigation

import android.content.Context
import android.provider.CalendarContract.Reminders
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseUser
import com.uvg.profitpulse.ui.screens.Screen
import com.uvg.profitpulse.ui.screens.HomeScreen
import com.uvg.profitpulse.ui.screens.reminders
import com.uvg.profitpulse.utils.AuthManager
import com.uvg.profitpulse.utils.RealtimeManager

@Composable
fun Navigation(context: Context, navController: NavHostController = rememberNavController()) {
    lateinit var realtimeManager: RealtimeManager
    lateinit var authManager: AuthManager
    val user: FirebaseUser? = authManager.getCurrentUser()

    Screen {
        NavHost(
            navController = navController,
            startDestination = if(user == null) Routes.Login.route else Routes.Home.route
        ) {
            composable(Routes.Home.route) {
                HomeScreen(realtimeManager = realtimeManager, authManager = authManager)
            }
        }
    }
}