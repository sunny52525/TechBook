package com.example.techbook.presentation.auth

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.techbook.domain.model.UiState

class AuthViewModel : ViewModel() {

    var email = mutableStateOf("")
        private set

    var password = mutableStateOf("")
        private set

    var isSignIn = mutableStateOf(true)
        private set


    var isError = mutableStateOf(UiState())
        private set

    fun setIsError(message: String = "", isError: Boolean = true) {
        this.isError.value = UiState(message = message, isError = isError, isLoading = false)
    }

    fun setLoading(isLoading: Boolean = true) {
        this.isError.value = UiState(message = "", isError = false, isLoading = isLoading)
    }


    fun setSignIn(isSignIn: Boolean) {
        this.isSignIn.value = isSignIn
    }


    fun setEmail(email: String) {
        this.email.value = email
    }

    fun setPassword(password: String) {
        this.password.value = password
    }


    fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}