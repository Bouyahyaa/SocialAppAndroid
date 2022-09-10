package com.example.socialapp.feature_auth.presentation.register

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialapp.core.util.Resource
import com.example.socialapp.feature_auth.domain.use_case.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val validateEmail: ValidateEmail = ValidateEmail(),
    private val validatePassword: ValidatePassword = ValidatePassword(),
    private val validateRepeatedPassword: ValidateRepeatedPassword = ValidateRepeatedPassword(),
    private val validateUsername: ValidateUsername = ValidateUsername(),
    private val registerUseCase: RegisterUseCase,
) : ViewModel() {

    private val _state = mutableStateOf(RegistrationFormState())
    val state: State<RegistrationFormState> = _state
    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    fun onEvent(event: RegistrationFormEvent) {
        when (event) {

            is RegistrationFormEvent.UsernameChanged -> {
                _state.value = state.value.copy(username = event.username)
            }

            is RegistrationFormEvent.EmailChanged -> {
                _state.value = state.value.copy(email = event.email)
            }

            is RegistrationFormEvent.PasswordChanged -> {
                _state.value = state.value.copy(password = event.password)
            }

            is RegistrationFormEvent.RepeatedPasswordChanged -> {
                _state.value = state.value.copy(repeatedPassword = event.repeatedPassword)
            }

            is RegistrationFormEvent.Submit -> {
                submitData()
            }
        }
    }


    private fun submitData() {
        val usernameResult = validateUsername.execute(_state.value.username)
        val emailResult = validateEmail.execute(_state.value.email)
        val passwordResult = validatePassword.execute(_state.value.password)
        val repeatedPasswordResult =
            validateRepeatedPassword.execute(_state.value.password, _state.value.repeatedPassword)

        val hasError = listOf(
            usernameResult,
            emailResult,
            passwordResult,
            repeatedPasswordResult,
        ).any { !it.successful }

        _state.value = state.value.copy(
            usernameError = usernameResult.errorMessage,
            emailError = emailResult.errorMessage,
            passwordError = passwordResult.errorMessage,
            repeatedPasswordError = repeatedPasswordResult.errorMessage,
        )

        if (hasError) {
            return
        }

        viewModelScope.launch {
            registerUseCase.invoke(
                username = _state.value.username,
                email = _state.value.email,
                password = _state.value.password,
                confirmPassword = _state.value.repeatedPassword
            ).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.value = state.value.copy(
                            isLoading = true,
                        )
                    }

                    is Resource.Success -> {
                        _state.value = state.value.copy(isLoading = false)
                        validationEventChannel.send(ValidationEvent.Success)
                    }

                    is Resource.Error -> {
                        _state.value = state.value.copy(
                            error = result.message!!,
                            isLoading = false
                        )
                        validationEventChannel.send(ValidationEvent.Error)
                    }
                }
            }
        }
    }

    sealed class ValidationEvent {
        object Success : ValidationEvent()
        object Error : ValidationEvent()
    }
}