package com.example.techbook.presentation.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.techbook.presentation.home.navigation.Routes
import com.example.techbook.presentation.home.screens.HomeScreen
import com.example.techbook.ui.theme.TechBookSocialMediaForProgrammersTheme
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeActivity : ComponentActivity() {
    private val auth by lazy {
        Firebase.auth
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TechBookSocialMediaForProgrammersTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()

                    Scaffold {


                        NavHost(
                            navController = navController,
                            startDestination = Routes.Home.route
                        ) {

                            composable(Routes.Home.route) {
                                HomeScreen()
                            }


                        }
                    }
                }
            }
        }
    }
}
