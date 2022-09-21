package com.example.socialapp.core.util

sealed class Screen(val route: String) {
    object SplashScreen : Screen("splash_screen")
    object RegistrationScreen : Screen("register_screen")
    object LoginScreen : Screen("login_screen")
    object ConfirmationScreen : Screen("confirmation_screen")
    object ForgetPasswordScreen : Screen("forget_password_screen")
    object VerifyTokenPassword : Screen("verify_token_password_screen")
    object ResetPasswordScreen : Screen("reset_password_screen")
    object PostsScreen : Screen("posts_screen")
    object ChatScreen : Screen("chat_screen")
    object ProfileScreen : Screen("profile_screen")
    object MainScreen : Screen("main_screen")
}