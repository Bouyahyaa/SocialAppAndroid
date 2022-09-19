package com.example.socialapp.feature_auth.presentation.ResetPassword

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
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

        LaunchedEffect(sheetState.currentValue) {
            when (sheetState.currentValue) {

                BottomSheetValue.Expanded -> {
                }

                BottomSheetValue.Collapsed -> {
                }
                else -> {
                    Log.i("Bottom Sheet State", "Bottom Sheet State")
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
                    Text(
                        text = "Bottom Sheet",
                        fontSize = 60.sp
                    )
                }
            },
            sheetBackgroundColor = Color.DarkGray,
            sheetPeekHeight = 0.dp,
            sheetShape = RoundedCornerShape(20.dp),
            sheetElevation = 20.dp,
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