package com.example.socialapp.feature_posts.presentation

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.socialapp.core.util.Screen
import com.example.socialapp.graphs.Graph

@Composable
fun BottomNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.POSTS,
        startDestination = Screen.PostsScreen.route
    ) {

        composable(Screen.PostsScreen.route) {
            Text(text = "posts")
        }

        composable(Screen.ChatScreen.route) {
            Text(text = "chat")
        }

        composable(Screen.ProfileScreen.route) {
            Text(text = "profile")
        }
    }
}