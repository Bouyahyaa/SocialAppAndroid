package com.example.socialapp.feature_auth.presentation.splash

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.socialapp.core.util.Screen
import com.example.socialapp.feature_auth.utils.ValidationEvent
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SplashViewModel = hiltViewModel(),
) {
    val context = LocalContext.current

    var startAnimation by remember {
        mutableStateOf(false)
    }

    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 3000
        )
    )

    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collect { event ->
            when (event) {

                is ValidationEvent.Success -> {
                    Log.e("Success", event.message)
                    delay(3500L)
                    navController.navigate(Screen.MainScreen.route) {
                        popUpTo(Screen.SplashScreen.route) {
                            inclusive = true
                        }
                    }
                }

                is ValidationEvent.Error -> {
                    Log.e("Error", event.error)
                    delay(3000L)
                    navController.navigate(Screen.LoginScreen.route) {
                        popUpTo(Screen.SplashScreen.route) {
                            inclusive = true
                        }
                    }
                }
            }
        }
    }

    LaunchedEffect(key1 = true) {
        startAnimation = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(
                colors = listOf(
                    Color(0xFFFA95AC),
                    Color(0xFFF71458)
                )
            )),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier
                    .size(120.dp)
                    .padding(10.dp)
                    .alpha(alpha = alphaAnim.value),
                imageVector = Icons.Default.Bento,
                contentDescription = "Logo Icon",
                tint = Color.White
            )
        }
        Text(
            text = " Social T Network ",
            modifier = Modifier.alpha(alpha = alphaAnim.value),
            color = Color.White,
            style = TextStyle(
                fontSize = 40.sp,
                fontFamily = FontFamily.Cursive,
            ),
        )
    }
}