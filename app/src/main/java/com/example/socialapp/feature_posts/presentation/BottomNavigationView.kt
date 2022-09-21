package com.example.socialapp.feature_posts.presentation

import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.socialapp.core.util.Screen
import com.example.socialapp.feature_posts.util.BottomNavItem

@Composable
fun BottomNavigationView(
    navController: NavHostController = rememberNavController(),
) {
    Scaffold(bottomBar = {
        BottomNavigationBar(
            items = listOf(
                BottomNavItem(
                    name = "Posts",
                    route = Screen.PostsScreen.route,
                    icon = Icons.Default.CalendarToday
                ),

                BottomNavItem(
                    name = "Chat",
                    route = Screen.ChatScreen.route,
                    icon = Icons.Default.Message,
                    badgeCount = 10
                ),

                BottomNavItem(
                    name = "Profile",
                    route = Screen.ProfileScreen.route,
                    icon = Icons.Default.Person
                )
            ),
            navController = navController,
            onItemClick = {
                navController.navigate(it.route)
            }
        )
    }
    ) {
        BottomNavigation(navController = navController)
    }
}