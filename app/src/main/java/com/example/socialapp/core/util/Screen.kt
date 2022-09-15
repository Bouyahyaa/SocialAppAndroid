package com.example.socialapp.core.util

sealed class Screen(val route: String) {
    object SplashScreen : Screen("splash_screen")
    object RegistrationScreen : Screen("register_screen")
    object LoginScreen : Screen("login_screen")
    object ConfirmationScreen : Screen("confirmation_screen")
    object MainScreen : Screen("main_screen")
}