package com.example.socialapp.feature_auth.presentation.confirmation

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.socialapp.feature_auth.presentation.confirmation.components.ConfirmationDialog
import com.example.socialapp.feature_auth.presentation.confirmation.components.ResendCodeDialog
import com.example.socialapp.feature_auth.utils.ValidationEvent

@Composable
fun ConfirmationScreen(
    navController: NavController,
    email: String?,
    viewModel: ConfirmationViewModel = hiltViewModel(),
) {
    val openDialogError = remember { mutableStateOf(false) }
    val dialogTextError = remember { mutableStateOf("") }

    val openDialogConfirmation = remember { mutableStateOf(false) }
    val openDialogResend = remember { mutableStateOf(false) }

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
        val state = viewModel.state.value
        val context = LocalContext.current

        LaunchedEffect(key1 = context) {
            viewModel.confirmationValidationEvents.collect { event ->
                when (event) {

                    is ValidationEvent.Success -> {
                        Log.e("Success", event.message)
                        openDialogConfirmation.value = true
                    }

                    is ValidationEvent.Error -> {
                        dialogTextError.value = event.error
                        openDialogError.value = true
                    }
                }
            }
        }

        LaunchedEffect(key1 = context) {
            viewModel.resendValidationEvents.collect { event ->
                when (event) {

                    is ValidationEvent.Success -> {
                        Log.e("SuccessResend", event.message)
                        openDialogResend.value = true
                    }

                    is ValidationEvent.Error -> {
                        dialogTextError.value = event.error
                        openDialogError.value = true
                    }
                }
            }
        }

        Column(modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center) {

            Image(painterResource(id = R.drawable.email),
                contentDescription = "Confirm button icon",
                modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.height(10.dp))

            Text(text = AnnotatedString("Verify your email"),
                style = TextStyle(fontSize = 17.sp,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray))

            Spacer(modifier = Modifier.height(15.dp))

            Text(text = AnnotatedString("Please enter the 5 digit code sent to"),
                style = TextStyle(fontSize = 17.sp,
                    fontFamily = FontFamily.Default,
                    color = Color.DarkGray))
            Text(text = AnnotatedString("your email : $email"),
                style = TextStyle(fontSize = 17.sp,
                    fontFamily = FontFamily.Default,
                    color = Color.DarkGray))

            Spacer(modifier = Modifier.height(20.dp))


            // Code
            TextFieldAuth(modifier = Modifier.align(Alignment.End),
                currentState = state.code,
                currentStatePlaceHolder = "Code",
                currentStateError = state.codeError,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                visualTransformation = VisualTransformation.None,
                onValueChange = {
                    viewModel.onEvent(ConfirmationFormEvent.CodeChanged(it))
                })

            Spacer(modifier = Modifier.height(20.dp))

            Box(modifier = Modifier.padding(30.dp, 0.dp, 30.dp, 0.dp)) {
                Button(
                    onClick = {
                        viewModel.onEvent(ConfirmationFormEvent.Submit)
                    },
                    shape = RoundedCornerShape(50.dp),
                    border = BorderStroke(1.dp,
                        Brush.horizontalGradient(
                            colors = listOf(Color(0xFFF71458), Color(0xFFFA95AC)),
                        )),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                ) {
                    Image(painterResource(id = R.drawable.baseline_mark_email_read_24),
                        contentDescription = "Confirm button icon",
                        modifier = Modifier.size(20.dp))
                    Text(text = "Confirm",
                        modifier = Modifier.padding(start = 10.dp, bottom = 2.dp))
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

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
            ClickableText(text = buildAnnotatedString {
                append("Didn't receive a code ? ")
                withStyle(style = SpanStyle(color = Color.Black, fontSize = 17.sp)) {
                    append("Resend")
                }
            },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(20.dp),
                onClick = {
                    viewModel.onEvent(ConfirmationFormEvent.ResendCode)
                },
                style = TextStyle(
                    color = Color.Gray,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic,
                    textAlign = TextAlign.Center,
                ))
        }

        AlertDialogSample(
            openDialog = openDialogError,
            dialogTitle = "Confirmation Error",
            dialogText = dialogTextError.value
        )

        ConfirmationDialog(
            openDialogCustom = openDialogConfirmation,
            onButtonClick = {
                navController.navigate(Screen.LoginScreen.route) {
                    popUpTo(Screen.ConfirmationScreen.route) {
                        inclusive = true
                    }
                }
            }
        )

        ResendCodeDialog(
            openDialogCustom = openDialogResend,
        )
    }
}