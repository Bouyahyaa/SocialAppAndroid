package com.example.socialapp.feature_auth.presentation.verify_token_password

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialapp.core.util.Resource
import com.example.socialapp.feature_auth.domain.use_case.ValidateToken
import com.example.socialapp.feature_auth.domain.use_case.VerifyTokenPasswordUseCase
import com.example.socialapp.feature_auth.utils.ValidationEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerifyTokenPasswordViewModel @Inject constructor(
    private val verifyTokenPasswordUseCase: VerifyTokenPasswordUseCase,
    private val validateToken: ValidateToken,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _state = mutableStateOf(VerifyTokenPasswordFormState())
    val state: State<VerifyTokenPasswordFormState> = _state
    val email = savedStateHandle.get<String>("email")

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    fun onEvent(event: VerifyTokenPasswordFormEvent) {
        when (event) {

            is VerifyTokenPasswordFormEvent.CodeChanged -> {
                _state.value = state.value.copy(code = event.code)
            }


            is VerifyTokenPasswordFormEvent.Submit -> {
                submitData()
            }
        }
    }


    private fun submitData() {
        val codeResult = validateToken.execute(_state.value.code)

        val hasError = listOf(
            codeResult,
        ).any { !it.successful }

        _state.value = state.value.copy(
            codeError = codeResult.errorMessage,
        )

        if (hasError) {
            return
        }

        viewModelScope.launch {
            verifyTokenPasswordUseCase.invoke(
                code = _state.value.code,
                email = email!!,
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