package com.example.socialapp.feature_auth.presentation.login

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.socialapp.R
import com.example.socialapp.core.util.Screen
import com.example.socialapp.feature_auth.presentation.components.AlertDialogSample
import com.example.socialapp.feature_auth.presentation.components.TextFieldAuth
import com.example.socialapp.feature_auth.utils.ValidationEvent
import com.example.socialapp.graphs.Graph
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.StyledPlayerView

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val openDialog = remember { mutableStateOf(false) }

    val dialogText = remember { mutableStateOf("") }

    Surface(modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background) {
        val state = viewModel.state.value
        val context = LocalContext.current

        LaunchedEffect(key1 = context) {
            viewModel.validationEvents.collect { event ->
                when (event) {

                    is ValidationEvent.Success -> {
                        Log.e("Success", event.message)
                        navController.navigate(Graph.POSTS)
                    }

                    is ValidationEvent.Error -> {
                        dialogText.value = event.error
                        openDialog.value = true
                    }
                }
            }
        }

        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = " Social T Network ",
                style = TextStyle(
                    fontSize = 40.sp,
                    fontFamily = FontFamily.Cursive,
                ),
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Email
            TextFieldAuth(
                modifier = Modifier.align(Alignment.End),
                currentState = state.email,
                currentStatePlaceHolder = "Email",
                currentStateError = state.emailError,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                ),
                visualTransformation = VisualTransformation.None,
                onValueChange = {
                    viewModel.onEvent(LoginFormEvent.EmailChanged(it))
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            //Password
            TextFieldAuth(
                modifier = Modifier.align(Alignment.End),
                currentState = state.password,
                currentStatePlaceHolder = "Password",
                currentStateError = state.passwordError,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                visualTransformation = PasswordVisualTransformation(),
                onValueChange = {
                    viewModel.onEvent(LoginFormEvent.PasswordChanged(it))
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            Box(modifier = Modifier.padding(30.dp, 0.dp, 30.dp, 0.dp)) {
                Button(
                    onClick = {
                        viewModel.onEvent(LoginFormEvent.Submit)
                    },
                    shape = RoundedCornerShape(50.dp),
                    border = BorderStroke(
                        1.dp,
                        Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFFF71458),
                                Color(0xFFFA95AC)
                            ),
                        )),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color.Black
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                ) {
                    Image(
                        painterResource(id = R.drawable.baseline_login_24),
                        contentDescription = "Login button icon",
                        modifier = Modifier.size(20.dp)
                    )
                    Text(text = "Login",
                        modifier = Modifier.padding(
                            start = 10.dp,
                            bottom = 2.dp
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            ClickableText(
                text = AnnotatedString("Forgot password ?"),
                onClick = {
                    navController.navigate(Screen.ForgetPasswordScreen.route) {
                        popUpTo(Screen.LoginScreen.route) {
                            inclusive = true
                        }
                    }
                },
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Default,
                    color = Color.DarkGray
                )
            )

            Spacer(modifier = Modifier.height(40.dp))

            if (state.isLoading) {
                LinearProgressIndicator(
                    color = MaterialTheme.colors.onSecondary,
                    backgroundColor = Color.LightGray,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .height(5.dp),
                )
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            ClickableText(
                text = buildAnnotatedString {
                    append("Don't have an account ? ")
                    withStyle(
                        style = SpanStyle(
                            color = Color.Black,
                            fontSize = 17.sp
                        )
                    ) {
                        append("Sign up")
                    }
                },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(20.dp),
                onClick = {
                    navController.navigate(Screen.RegistrationScreen.route) {
                        popUpTo(Screen.LoginScreen.route) {
                            inclusive = true
                        }
                    }
                },
                style = TextStyle(
                    color = Color.Gray,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic,
                    textAlign = TextAlign.Center,
                )
            )
        }

        AlertDialogSample(
            openDialog = openDialog,
            dialogTitle = "Login Error",
            dialogText = dialogText.value
        )

        BackHandler {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }
}

private fun Context.buildExoPlayer(uri: Uri) =
    ExoPlayer.Builder(this).build().apply {
        setMediaItem(MediaItem.fromUri(uri))
        repeatMode = Player.REPEAT_MODE_ALL
        playWhenReady = true
        prepare()
    }

private fun Context.buildPlayerView(exoPlayer: ExoPlayer) =
    StyledPlayerView(this).apply {
        player = exoPlayer
        layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT)
        useController = false
        resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
    }