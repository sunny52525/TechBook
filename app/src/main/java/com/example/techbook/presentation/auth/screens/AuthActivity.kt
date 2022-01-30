package com.example.techbook.presentation.auth.screens

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import com.example.techbook.common.ExtensionFunctions.showToast
import com.example.techbook.presentation.auth.AuthViewModel
import com.example.techbook.presentation.components.ErrorDialog
import com.example.techbook.presentation.components.LoadingDialog
import com.example.techbook.presentation.home.HomeActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class)
class AuthActivity : ComponentActivity() {

    private val auth by lazy {
        Firebase.auth
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel by viewModels<AuthViewModel>()
        setContent {
            val email by viewModel.email

            val password by viewModel.password
            val isSignIn by viewModel.isSignIn


            val isError by viewModel.isError

            if (isError.isLoading) {

                LoadingDialog()
            }
            if (isError.isError) {
                showToast("error")
                ErrorDialog(isError.message) {
                    viewModel.setIsError(isError = false)
                }
            }



            LoginScreen(
                email = email,
                password = password,
                verifySignIn = {
                    viewModel.setLoading()
                    if (isSignIn) {
                        signInUser(
                            email = email.trim(),
                            password = password.trim()
                        ) { errorString ->
                            Log.d("TAG", "onCreate: $errorString")
                            viewModel.setIsError(
                                error = errorString,
                            )
                        }
                    } else {
                        signUpUser(
                            email = email.trim(),
                            password = password.trim()
                        ) { errorString ->
                            Log.d("TAG", "onCreate: $errorString")

                            viewModel.setIsError(
                                error = errorString,
                            )
                        }
                    }
                },
                onEmailChange = viewModel::setEmail,
                onPasswordChange = viewModel::setPassword,
                onRegisterClicked = {
                    viewModel.setSignIn(isSignIn.not())
                },
                isSignIn = isSignIn,
            )

        }


    }


    private fun signInUser(email: String, password: String, onError: (String) -> Unit = {}) {


        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()

                } else {
                    onError(task.exception?.message.toString())
                }
            }
    }

    private fun signUpUser(email: String, password: String, onError: (String) -> Unit = {}) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                } else {
                    onError(task.exception?.message.toString())
                }
            }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            // User is signed in
            Intent(this, HomeActivity::class.java).also {
                startActivity(it)
                finish()

            }
        }
    }
}