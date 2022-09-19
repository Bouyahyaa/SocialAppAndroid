package com.example.socialapp.feature_auth.presentation.ResetPassword

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialapp.core.util.Resource
import com.example.socialapp.feature_auth.domain.use_case.ResetPasswordUseCase
import com.example.socialapp.feature_auth.domain.use_case.ValidatePassword
import com.example.socialapp.feature_auth.domain.use_case.ValidateRepeatedPassword
import com.example.socialapp.feature_auth.utils.ValidationEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val resetPasswordUseCase: ResetPasswordUseCase,
    private val validatePassword: ValidatePassword,
    private val validateRepeatedPassword: ValidateRepeatedPassword,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _state = mutableStateOf(ResetPasswordFormState())
    val state: State<ResetPasswordFormState> = _state
    val email = savedStateHandle.get<String>("email")
    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()


    fun onEvent(event: ResetPasswordFormEvent) {
        when (event) {

            is ResetPasswordFormEvent.PasswordChanged -> {
                _state.value = state.value.copy(password = event.password)
            }

            is ResetPasswordFormEvent.RepeatedPasswordChanged -> {
                _state.value = state.value.copy(repeatedPassword = event.repeatedPassword)
            }

            is ResetPasswordFormEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun submitData() {

        val passwordResult = validatePassword.execute(_state.value.password)
        val repeatedPasswordResult =
            validateRepeatedPassword.execute(_state.value.password, _state.value.repeatedPassword)

        val hasError = listOf(
            passwordResult,
            repeatedPasswordResult,
        ).any { !it.successful }

        _state.value = state.value.copy(
            passwordError = passwordResult.errorMessage,
            repeatedPasswordError = repeatedPasswordResult.errorMessage,
        )

        if (hasError) {
            return
        }

        viewModelScope.launch {
            resetPasswordUseCase.invoke(
                email = email!!,
                password = _state.value.password,
                confirmPassword = _state.value.repeatedPassword
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