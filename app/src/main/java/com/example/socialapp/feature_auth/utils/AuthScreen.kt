package com.example.socialapp.feature_auth.utils

sealed class AuthScreen(val route: String) {
    object RegistrationScreen : AuthScreen("register_screen")
    object LoginScreen : AuthScreen("login_screen")
}