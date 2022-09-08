package com.example.socialapp.feature_auth.util

sealed class AuthError {
    object FieldEmpty : AuthError()
    object InputTooShort : AuthError()
    object InvalidEmail : AuthError()
    object InvalidPassword : AuthError()
}