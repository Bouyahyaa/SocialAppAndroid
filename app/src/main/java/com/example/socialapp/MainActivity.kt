package com.example.socialapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.socialapp.feature_auth.presentation.login.LoginScreen
import com.example.socialapp.feature_auth.utils.AuthScreen
import com.example.socialapp.feature_auth.presentation.register.RegistrationScreen
import com.example.socialapp.ui.theme.SocialAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SocialAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = AuthScreen.RegistrationScreen.route
                    ) {
                        composable(route = AuthScreen.RegistrationScreen.route) {
                            RegistrationScreen(navController = navController)
                        }

                        composable(route = AuthScreen.LoginScreen.route) {
                            LoginScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}