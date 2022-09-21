package com.example.socialapp.graphs

import androidx.navigation.*
import androidx.navigation.compose.composable
import com.example.socialapp.core.util.Screen
import com.example.socialapp.feature_auth.presentation.ResetPassword.ResetPasswordScreen
import com.example.socialapp.feature_auth.presentation.confirmation.ConfirmationScreen
import com.example.socialapp.feature_auth.presentation.forget_password.ForgetPasswordScreen
import com.example.socialapp.feature_auth.presentation.login.LoginScreen
import com.example.socialapp.feature_auth.presentation.register.RegistrationScreen
import com.example.socialapp.feature_auth.presentation.splash.SplashScreen
import com.example.socialapp.feature_auth.presentation.verify_token_password.VerifyTokenPasswordScreen

fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.AUTHENTICATION,
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

        composable(route = Screen.ForgetPasswordScreen.route) {
            ForgetPasswordScreen(navController = navController)
        }

        composable(route = Screen.VerifyTokenPassword.route + "/{email}",
            arguments = listOf(
                navArgument("email") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = true
                },
            )) { entry ->
            VerifyTokenPasswordScreen(
                navController = navController,
                email = entry.arguments?.getString("email")!!,
            )
        }

        composable(route = Screen.ResetPasswordScreen.route + "/{email}",
            arguments = listOf(
                navArgument("email") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = true
                },
            )) {
            ResetPasswordScreen(
                navController = navController,
            )
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
    }
}