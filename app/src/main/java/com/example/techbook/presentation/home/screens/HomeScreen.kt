package com.example.techbook.presentation.home.screens

import android.app.Activity
import android.content.Intent
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.techbook.presentation.auth.screens.AuthActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


@Composable
fun HomeScreen() {

    val context = LocalContext.current as Activity
    Button(onClick = {
        Firebase.auth.signOut()
        context.finish()
        Intent(context, AuthActivity::class.java).also {
            context.startActivity(it)
        }
    }) {
        Text("Logout")
    }

}