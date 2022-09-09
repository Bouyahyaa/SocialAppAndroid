package com.example.socialapp.feature_auth

sealed class AuthScreen(val route: String) {
    object RegistrationScreen : AuthScreen("register_screen")
}
