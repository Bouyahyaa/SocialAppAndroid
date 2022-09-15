package com.example.socialapp.feature_auth.presentation.confirmation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialapp.core.util.Resource
import com.example.socialapp.feature_auth.domain.use_case.ConfirmationUseCase
import com.example.socialapp.feature_auth.domain.use_case.ValidateToken
import com.example.socialapp.feature_auth.utils.ValidationEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConfirmationViewModel @Inject constructor(
    private val confirmationUseCase: ConfirmationUseCase,
    private val validateToken: ValidateToken,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _state = mutableStateOf(ConfirmationFormState())
    val state: State<ConfirmationFormState> = _state
    val email = savedStateHandle.get<String>("email")
    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    fun onEvent(event: ConfirmationFormEvent) {
        when (event) {

            is ConfirmationFormEvent.CodeChanged -> {
                _state.value = state.value.copy(code = event.code)
            }

            is ConfirmationFormEvent.Submit -> {
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
            confirmationUseCase.invoke(
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