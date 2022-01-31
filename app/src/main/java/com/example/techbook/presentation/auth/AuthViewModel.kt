package com.example.techbook.presentation.auth

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.techbook.domain.model.UiState
import com.example.techbook.domain.model.UserModel
import com.example.techbook.domain.use_case.CreateUserUserCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val createUserUserCase: CreateUserUserCase
) : ViewModel() {

    var email = mutableStateOf("")
        private set

    var password = mutableStateOf("")
        private set

    var isSignIn = mutableStateOf(true)
        private set


    var isError = mutableStateOf(UiState())
        private set

    var name = mutableStateOf("")
        private set

    var year = mutableStateOf("")
        private set
    var college = mutableStateOf("Select College")
        private set

    var referral = mutableStateOf("")
        private set

    fun setReferral(referral: String) {
        this.referral.value = referral
    }

    fun setCollege(college: String) {
        this.college.value = college
    }

    fun setYear(year: String) {
        this.year.value = year
    }

    fun setName(name: String) {
        this.name.value = name
    }


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
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()
    }


    fun createUser(onSuccess: () -> Unit) {
        createUserUserCase(
            referral =referral.value,
            user = UserModel(
                name = name.value,
                college = college.value,
                email = email.value,
                year = year.value,
            ), onSuccess = {
                onSuccess()

            }, onError = {

                setIsError(it.toString())
            },

        )
    }

}