package com.example.socialapp.feature_posts.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.socialapp.core.util.Screen
import com.example.socialapp.feature_posts.presentation.pictures.PictureListScreen
import com.example.socialapp.feature_posts.presentation.stories.StoriesScreen

@Composable
fun PostsView(
    authNavController: NavController,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Screen.PictureListScreen.route
    ) {
        composable(route = Screen.PictureListScreen.route) {
            PictureListScreen(
                authNavController = authNavController,
                navController = navController
            )
        }

        composable(
            route = Screen.StoriesScreen.route + "/{pictureUrl}/{userPictureUrl}/{username}/{pictureId}",
            arguments = listOf(
                navArgument("pictureUrl") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = true
                },
                navArgument("userPictureUrl") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = true
                },
                navArgument("username") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = true
                },
                navArgument("pictureId") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) { entry ->
            StoriesScreen(
                userPictureUrl = entry.arguments?.getString("userPictureUrl")!!,
                username = entry.arguments?.getString("username")!!,
                pictureId = entry.arguments?.getString("pictureId")!!,
                navController = navController
            )
        }
    }
}