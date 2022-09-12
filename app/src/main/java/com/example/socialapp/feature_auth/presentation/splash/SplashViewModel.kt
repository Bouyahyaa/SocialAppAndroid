package com.example.socialapp.feature_auth.presentation.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialapp.core.util.Resource
import com.example.socialapp.feature_auth.domain.use_case.AuthenticateUseCase
import com.example.socialapp.feature_auth.utils.ValidationEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authenticateUseCase: AuthenticateUseCase,
) : ViewModel() {
    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    init {
        authenticate()
    }

    private fun authenticate() {
        viewModelScope.launch {
            authenticateUseCase.invoke().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        Log.e("Loading", "Loading")
                    }

                    is Resource.Success -> {
                        validationEventChannel.send(ValidationEvent.Success("Authorized"))
                    }

                    is Resource.Error -> {
                        validationEventChannel.send(ValidationEvent.Error(result.message!!))
                    }
                }
            }
        }
    }
}