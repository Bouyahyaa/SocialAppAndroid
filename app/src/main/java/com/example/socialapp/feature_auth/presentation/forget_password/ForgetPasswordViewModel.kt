package com.example.socialapp.feature_auth.presentation.forget_password

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialapp.core.util.Resource
import com.example.socialapp.feature_auth.domain.use_case.ForgetPasswordUseCase
import com.example.socialapp.feature_auth.domain.use_case.ValidateEmail
import com.example.socialapp.feature_auth.utils.ValidationEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgetPasswordViewModel @Inject constructor(
    private val validateEmail: ValidateEmail = ValidateEmail(),
    private val forgetPasswordUseCase: ForgetPasswordUseCase,
) : ViewModel() {
    private val _state = mutableStateOf(ForgetPasswordFormState())
    val state: State<ForgetPasswordFormState> = _state
    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    fun onEvent(event: ForgetPasswordFormEvent) {
        when (event) {

            is ForgetPasswordFormEvent.EmailChanged -> {
                _state.value = state.value.copy(email = event.email)
            }

            is ForgetPasswordFormEvent.Submit -> {
                submitData()
            }
        }
    }


    private fun submitData() {
        val emailResult = validateEmail.execute(_state.value.email)

        val hasError = listOf(
            emailResult,
        ).any { !it.successful }

        _state.value = state.value.copy(
            emailError = emailResult.errorMessage,
        )

        if (hasError) {
            return
        }

        viewModelScope.launch {
            forgetPasswordUseCase.invoke(
                email = _state.value.email,
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