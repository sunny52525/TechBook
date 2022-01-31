package com.example.techbook.presentation.auth.screens

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.techbook.common.ExtensionFunctions.showToast
import com.example.techbook.presentation.auth.AuthViewModel
import com.example.techbook.presentation.components.ErrorDialog
import com.example.techbook.presentation.components.LoadingDialog
import com.example.techbook.presentation.home.HomeActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class)
class AuthActivity : ComponentActivity() {

    private val viewModel by viewModels<AuthViewModel>()
    private val auth by lazy {
        Firebase.auth
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val email by viewModel.email

            val password by viewModel.password
            val isSignIn by viewModel.isSignIn

            val name by viewModel.name
            val year by viewModel.year
            val college by viewModel.college

            val refer by viewModel.referral
            var showReferMessage by remember {
                mutableStateOf(true)
            }

            var dropdownExpanded by remember { mutableStateOf(false) }

            val isError by viewModel.isError

            if (isError.isLoading) {

                LoadingDialog()
            }
            if (isError.isError) {
                ErrorDialog(isError.message) {
                    viewModel.setIsError(isError = false)
                }
            }

            if (refer.isNotEmpty() && showReferMessage) {
                ErrorDialog("You are referred by, Complete your profile to get 10 points") {
                    showReferMessage = false
                }
            }


            LoginScreen(
                email = email,
                password = password,
                name = name,
                year = year,
                college = college,
                verifySignIn = {

                    if (viewModel.isValidEmail(email = email).not()) {
                        viewModel.setIsError(isError = true, message = "Email is not valid")
                    } else {

                        viewModel.setLoading()
                        if (isSignIn) {
                            signInUser(
                                email = email.trim(),
                                password = password.trim()
                            ) { errorString ->
                                Log.d("TAG", "onCreate: $errorString")
                                viewModel.setIsError(
                                    message = errorString,
                                )
                            }
                        } else {
                            signUpUser(
                                email = email.trim(),
                                password = password.trim()
                            ) { errorString ->
                                Log.d("TAG", "onCreate: $errorString")

                                viewModel.setIsError(
                                    message = errorString,
                                )
                            }
                        }

                    }
                },
                onEmailChange = viewModel::setEmail,
                onPasswordChange = viewModel::setPassword,
                onRegisterClicked = {
                    viewModel.setSignIn(isSignIn.not())
                },
                isSignIn = isSignIn,
                onForgotPasswordClicked = {

                    if (email.trim().isBlank()) {
                        showToast("Please enter email")
                    } else {
                        auth.sendPasswordResetEmail(email.trim())
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    viewModel.setIsError(
                                        message = "Password reset email sent",
                                    )
                                } else {
                                    viewModel.setIsError(
                                        message = task.exception?.message.toString(),
                                    )
                                }
                            }
                    }

                },
                onYearChanged = viewModel::setYear,
                onCollegeChanged = viewModel::setCollege,
                onNameChanged = viewModel::setName,
                dropDownExpanded = dropdownExpanded,
                onDropDownClicked = {

                    dropdownExpanded = dropdownExpanded.not()
                }
            )

        }

        installSplashScreen()

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

                    viewModel.createUser {
                        startActivity(Intent(this, HomeActivity::class.java))
                        finish()
                    }

                } else {
                    onError(task.exception?.message.toString())
                }
            }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val appLinkAction = intent?.action
        val appLinkData: Uri? = intent?.data
        if (Intent.ACTION_VIEW == appLinkAction) {
            Log.d(TAG, "onNewIntent: " + appLinkData?.toString())
            appLinkData?.lastPathSegment?.let {
                Log.d(TAG, "onNewIntent: $it")
                viewModel.setReferral(it)
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

    companion object {
        private const val TAG = "AuthActivity"
    }
}