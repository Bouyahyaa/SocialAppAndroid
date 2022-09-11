package com.example.socialapp.feature_auth.presentation.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialapp.core.util.Resource
import com.example.socialapp.feature_auth.domain.use_case.LoginUseCase
import com.example.socialapp.feature_auth.domain.use_case.ValidateEmail
import com.example.socialapp.feature_auth.domain.use_case.ValidatePassword
import com.example.socialapp.feature_auth.utils.ValidationEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val validateEmail: ValidateEmail = ValidateEmail(),
    private val validatePassword: ValidatePassword = ValidatePassword(),
    private val loginUseCase: LoginUseCase,
) : ViewModel() {
    private val _state = mutableStateOf(LoginFormState())
    val state: State<LoginFormState> = _state
    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    fun onEvent(event: LoginFormEvent) {
        when (event) {

            is LoginFormEvent.EmailChanged -> {
                _state.value = state.value.copy(email = event.email)
            }

            is LoginFormEvent.PasswordChanged -> {
                _state.value = state.value.copy(password = event.password)
            }

            is LoginFormEvent.Submit -> {
                submitData()
            }
        }
    }


    private fun submitData() {
        val emailResult = validateEmail.execute(_state.value.email)
        val passwordResult = validatePassword.execute(_state.value.password)

        val hasError = listOf(
            emailResult,
            passwordResult,
        ).any { !it.successful }

        _state.value = state.value.copy(
            emailError = emailResult.errorMessage,
            passwordError = passwordResult.errorMessage,
        )

        if (hasError) {
            return
        }

        viewModelScope.launch {
            loginUseCase.invoke(
                email = _state.value.email,
                password = _state.value.password,
            ).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.value = state.value.copy(
                            isLoading = true,
                            error = ""
                        )
                    }

                    is Resource.Success -> {
                        _state.value = state.value.copy(
                            isLoading = false,
                            error = ""
                        )
                        validationEventChannel.send(ValidationEvent.Success(result.data?.message!!))
                    }

                    is Resource.Error -> {
                        _state.value = state.value.copy(
                            isLoading = false,
                            error = result.message!!,
                        )
                        validationEventChannel.send(ValidationEvent.Error(_state.value.error))
                    }
                }
            }
        }
    }
}