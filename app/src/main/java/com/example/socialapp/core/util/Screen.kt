package com.example.socialapp.core.util

sealed class Screen(val route: String) {
    object SplashScreen : Screen("splash_screen")
    object RegistrationScreen : Screen("register_screen")
    object LoginScreen : Screen("login_screen")
    object ConfirmationScreen : Screen("confirmation_screen")
    object ForgetPasswordScreen : Screen("forget_password_screen")
    object VerifyTokenPassword : Screen("verify_token_password_screen")
    object ResetPassword : Screen("reset_password_screen")
    object MainScreen : Screen("main_screen")
}