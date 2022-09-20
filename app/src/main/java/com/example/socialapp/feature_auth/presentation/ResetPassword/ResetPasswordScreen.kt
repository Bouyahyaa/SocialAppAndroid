package com.example.socialapp.feature_auth.presentation.ResetPassword

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.socialapp.R
import com.example.socialapp.core.util.Screen
import com.example.socialapp.feature_auth.presentation.components.AlertDialogSample
import com.example.socialapp.feature_auth.presentation.components.TextFieldAuth
import com.example.socialapp.feature_auth.utils.ValidationEvent

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ResetPasswordScreen(
    navController: NavController,
    viewModel: ResetPasswordViewModel = hiltViewModel(),
) {
    val openDialogError = remember { mutableStateOf(false) }
    val dialogTextError = remember { mutableStateOf("") }

    val sheetState = rememberBottomSheetState(
        initialValue = BottomSheetValue.Collapsed,
    )
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
        val state = viewModel.state.value
        val context = LocalContext.current

        LaunchedEffect(key1 = context) {
            viewModel.validationEvents.collect { event ->
                when (event) {

                    is ValidationEvent.Success -> {
                        Log.e("Success", event.message)
                        sheetState.expand()
                    }

                    is ValidationEvent.Error -> {
                        dialogTextError.value = event.error
                        openDialogError.value = true
                    }
                }
            }
        }

        BottomSheetScaffold(
            scaffoldState = scaffoldState,
            sheetContent = {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(Modifier.background(Color.White)) {
                        Image(
                            painter = painterResource(id = R.drawable.baseline_login_24),
                            contentDescription = null, // decorative
                            contentScale = ContentScale.Fit,
                            colorFilter = ColorFilter.tint(color = Color(0xFFFA95AC)),
                            modifier = Modifier
                                .padding(top = 35.dp)
                                .height(70.dp)
                                .fillMaxWidth(),

                            )

                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Done!",
                                fontWeight = FontWeight.ExtraBold,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .padding(top = 5.dp)
                                    .fillMaxWidth(),
                                style = MaterialTheme.typography.h6,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = "Yahoo! You can now Log In with your new password",
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                                    .fillMaxWidth(),
                                style = MaterialTheme.typography.body1
                            )
                        }

                        Row(Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp)
                            .background(brush = Brush.horizontalGradient(
                                colors = listOf(Color(0xFFF71458), Color(0xFFFA95AC)),
                            )), horizontalArrangement = Arrangement.SpaceAround) {

                            TextButton(onClick = {
                                navController.navigate(Screen.LoginScreen.route) {
                                    popUpTo(Screen.ResetPassword.route) {
                                        inclusive = true
                                    }
                                }
                            }) {
                                Text("Log In",
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color.White,
                                    modifier = Modifier.padding(top = 5.dp, bottom = 5.dp))
                            }
                        }
                    }
                }
            },
            sheetBackgroundColor = Color.White,
            sheetShape = RoundedCornerShape(20.dp),
            sheetElevation = 20.dp,
            sheetPeekHeight = 0.dp,
            sheetGesturesEnabled = false,
        ) {
            Column(modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center) {

                Image(painterResource(id = R.drawable.password_rework),
                    contentDescription = "Reset button icon",
                    modifier = Modifier.fillMaxWidth())

                Spacer(modifier = Modifier.height(20.dp))

                Text(text = AnnotatedString(" Enter new password ! "),
                    style = TextStyle(fontSize = 17.sp,
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight.Bold,
                        color = Color.DarkGray))

                Spacer(modifier = Modifier.height(15.dp))

                Text(text = AnnotatedString("Set the new password for your account so"),
                    style = TextStyle(fontSize = 17.sp,
                        fontFamily = FontFamily.Default,
                        color = Color.DarkGray))
                Text(text = AnnotatedString("you can login and access all features"),
                    style = TextStyle(fontSize = 17.sp,
                        fontFamily = FontFamily.Default,
                        color = Color.DarkGray))

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
                        viewModel.onEvent(ResetPasswordFormEvent.PasswordChanged(it))
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
                        viewModel.onEvent(ResetPasswordFormEvent.RepeatedPasswordChanged(it))
                    }
                )

                Spacer(modifier = Modifier.height(30.dp))

                Box(modifier = Modifier.padding(30.dp, 0.dp, 30.dp, 0.dp)) {
                    Button(
                        onClick = {
                            viewModel.onEvent(ResetPasswordFormEvent.Submit)
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
                        Image(painterResource(id = R.drawable.baseline_autorenew_24),
                            contentDescription = "Reset button icon",
                            modifier = Modifier.size(20.dp))
                        Text(text = "Reset",
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
        }

        AlertDialogSample(
            openDialog = openDialogError,
            dialogTitle = "Reset Error",
            dialogText = dialogTextError.value
        )

        BackHandler {
            navController.navigate(Screen.ForgetPasswordScreen.route) {
                popUpTo(Screen.ResetPassword.route) {
                    inclusive = true
                }
            }
        }
    }
}