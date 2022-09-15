package com.example.socialapp.feature_auth.presentation.register

import android.util.Log
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.socialapp.R
import com.example.socialapp.core.util.Screen
import com.example.socialapp.feature_auth.presentation.components.AlertDialogSample
import com.example.socialapp.feature_auth.presentation.components.TextFieldAuth
import com.example.socialapp.feature_auth.utils.ValidationEvent

@Composable
fun RegistrationScreen(
    navController: NavController,
    viewModel: RegistrationViewModel = hiltViewModel(),
) {
    val openDialog = remember { mutableStateOf(false) }

    val dialogText = remember { mutableStateOf("") }

    Surface(modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background) {
        val state = viewModel.state.value
        LaunchedEffect(key1 = state) {
            viewModel.validationEvents.collect { event ->
                when (event) {
                    is ValidationEvent.Success -> {
                        Log.e("Success", event.message)
                        navController.navigate(Screen.ConfirmationScreen.route + "/${state.email}") {
                            popUpTo(Screen.RegistrationScreen.route) {
                                inclusive = true
                            }
                        }
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

            // Username
            TextFieldAuth(
                modifier = Modifier.align(Alignment.End),
                currentState = state.username,
                currentStatePlaceHolder = "Username",
                currentStateError = state.usernameError,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                ),
                visualTransformation = VisualTransformation.None,
                onValueChange = {
                    viewModel.onEvent(RegistrationFormEvent.UsernameChanged(it))
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Email
            TextFieldAuth(
                modifier = Modifier.align(Alignment.End),
                currentState = state.email,
                currentStatePlaceHolder = "Email",
                currentStateError = state.emailError,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ),
                visualTransformation = VisualTransformation.None,
                onValueChange = {
                    viewModel.onEvent(RegistrationFormEvent.EmailChanged(it))
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
                    viewModel.onEvent(RegistrationFormEvent.PasswordChanged(it))
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            //RepeatedPassword
            TextFieldAuth(
                modifier = Modifier.align(Alignment.End),
                currentState = state.repeatedPassword,
                currentStatePlaceHolder = "Repeat Password",
                currentStateError = state.repeatedPasswordError,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                visualTransformation = PasswordVisualTransformation(),
                onValueChange = {
                    viewModel.onEvent(RegistrationFormEvent.RepeatedPasswordChanged(it))
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            Box(modifier = Modifier.padding(30.dp, 0.dp, 30.dp, 0.dp)) {
                Button(
                    onClick = {
                        viewModel.onEvent(RegistrationFormEvent.Submit)
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
                        painterResource(id = R.drawable.baseline_how_to_reg_24),
                        contentDescription = "Registration button icon",
                        modifier = Modifier.size(20.dp)
                    )
                    Text(text = "Register",
                        modifier = Modifier.padding(
                            start = 10.dp,
                            bottom = 2.dp
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(25.dp))

            if (state.isLoading) {
                CircularProgressIndicator(
                    color = MaterialTheme.colors.error,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                )
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            ClickableText(
                text = buildAnnotatedString {
                    append(" Have an account ? ")
                    withStyle(
                        style = SpanStyle(
                            color = Color.Black,
                            fontSize = 17.sp
                        )
                    ) {
                        append("Log in")
                    }
                },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(20.dp),
                onClick = {
                    navController.navigate(Screen.LoginScreen.route) {
                        popUpTo(Screen.RegistrationScreen.route) {
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
            dialogTitle = "Registration Error",
            dialogText = dialogText.value
        )

        BackHandler {
            navController.navigate(Screen.LoginScreen.route) {
                popUpTo(Screen.RegistrationScreen.route) {
                    inclusive = true
                }
            }
        }
    }
}