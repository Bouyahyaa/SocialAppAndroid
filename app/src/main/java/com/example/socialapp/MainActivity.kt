package com.example.socialapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.socialapp.feature_auth.presentation.login.LoginScreen
import com.example.socialapp.core.util.Screen
import com.example.socialapp.feature_auth.presentation.confirmation.ConfirmationScreen
import com.example.socialapp.feature_auth.presentation.register.RegistrationScreen
import com.example.socialapp.feature_auth.presentation.splash.SplashScreen
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
                        startDestination = Screen.SplashScreen.route
                    ) {
                        composable(route = Screen.SplashScreen.route) {
                            SplashScreen(navController = navController)
                        }

                        composable(route = Screen.RegistrationScreen.route) {
                            RegistrationScreen(navController = navController)
                        }

                        composable(route = Screen.LoginScreen.route) {
                            LoginScreen(navController = navController)
                        }

                        composable(route = Screen.ConfirmationScreen.route + "/{email}",
                            arguments = listOf(
                                navArgument("email") {
                                    type = NavType.StringType
                                    defaultValue = ""
                                    nullable = true
                                },
                            )
                        ) { entry ->
                            ConfirmationScreen(
                                navController = navController,
                                email = entry.arguments?.getString("email")!!,
                            )
                        }

                        composable(route = Screen.MainScreen.route) {
                            MainScreen()
                        }
                    }
                }
            }
        }
    }
}