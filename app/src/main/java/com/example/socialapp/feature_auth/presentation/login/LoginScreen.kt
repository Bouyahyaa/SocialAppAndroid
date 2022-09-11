package com.example.socialapp.feature_auth.presentation.login

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.socialapp.feature_auth.utils.ValidationEvent

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    Surface(modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background) {
        val state = viewModel.state.value
        val context = LocalContext.current
        LaunchedEffect(key1 = context) {
            viewModel.validationEvents.collect { event ->
                when (event) {
                    is ValidationEvent.Success -> {
                        Toast.makeText(context,
                            event.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    is ValidationEvent.Error -> {
                        Toast.makeText(context,
                            event.error,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center
        ) {

            // Email
            TextField(
                value = state.email,
                onValueChange = {
                    viewModel.onEvent(LoginFormEvent.EmailChanged(it))
                },
                isError = state.emailError != null,
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(text = "Email")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                )
            )

            if (state.emailError != null) {
                Text(
                    text = state.emailError,
                    color = MaterialTheme.colors.error,
                    modifier = Modifier.align(Alignment.End),
                )
            }

            Spacer(modifier = Modifier.height(16.dp))


            //Password
            TextField(
                value = state.password,
                onValueChange = {
                    viewModel.onEvent(LoginFormEvent.PasswordChanged(it))
                },
                isError = state.passwordError != null,
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(text = "Password")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                visualTransformation = PasswordVisualTransformation()
            )

            if (state.passwordError != null) {
                Text(
                    text = state.passwordError,
                    color = MaterialTheme.colors.error,
                    modifier = Modifier.align(Alignment.End),
                )
            }

            Spacer(modifier = Modifier.height(16.dp))


            Button(
                modifier = Modifier.align(Alignment.End),
                onClick = {
                    viewModel.onEvent(LoginFormEvent.Submit)
                }) {
                Text(text = "Submit")
            }

            if (state.isLoading) {
                CircularProgressIndicator(
                    color = MaterialTheme.colors.error,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                )
            }
        }
    }
}