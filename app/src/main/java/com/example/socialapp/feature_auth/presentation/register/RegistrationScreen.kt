package com.example.socialapp.feature_auth.presentation.register

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

@Composable
fun RegistrationScreen(
    navController: NavController,
    viewModel: RegistrationViewModel = hiltViewModel(),
) {
    Surface(modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background) {
        val state = viewModel.state.value
        val context = LocalContext.current
        LaunchedEffect(key1 = context) {
            viewModel.validationEvents.collect { event ->
                when (event) {
                    is RegistrationViewModel.ValidationEvent.Success -> {
                        Toast.makeText(context,
                            "Registration Successful",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    is RegistrationViewModel.ValidationEvent.Error -> {
                        Toast.makeText(context,
                            state.error,
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

            // Username
            TextField(
                value = state.username,
                onValueChange = {
                    viewModel.onEvent(RegistrationFormEvent.UsernameChanged(it))
                },
                isError = state.usernameError != null,
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(text = "Username")
                },
            )

            if (state.usernameError != null) {
                Text(
                    text = state.usernameError,
                    color = MaterialTheme.colors.error,
                    modifier = Modifier.align(Alignment.End),
                )
            }

            Spacer(modifier = Modifier.height(16.dp))


            // Email
            TextField(
                value = state.email,
                onValueChange = {
                    viewModel.onEvent(RegistrationFormEvent.EmailChanged(it))
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
                    viewModel.onEvent(RegistrationFormEvent.PasswordChanged(it))
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


            //RepeatedPassword
            TextField(
                value = state.repeatedPassword,
                onValueChange = {
                    viewModel.onEvent(RegistrationFormEvent.RepeatedPasswordChanged(it))
                },
                isError = state.repeatedPasswordError != null,
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(text = "Repeat Password")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                visualTransformation = PasswordVisualTransformation()
            )

            if (state.repeatedPasswordError != null) {
                Text(
                    text = state.repeatedPasswordError,
                    color = MaterialTheme.colors.error,
                    modifier = Modifier.align(Alignment.End),
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                modifier = Modifier.align(Alignment.End),
                onClick = {
                    viewModel.onEvent(RegistrationFormEvent.Submit)
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