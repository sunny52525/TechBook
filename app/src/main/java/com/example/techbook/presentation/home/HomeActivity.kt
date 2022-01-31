package com.example.techbook.presentation.home

import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.techbook.common.Resource
import com.example.techbook.presentation.components.ErrorDialog
import com.example.techbook.presentation.components.LoadingDialog
import com.example.techbook.presentation.home.components.NavigationBar
import com.example.techbook.presentation.home.navigation.Routes
import com.example.techbook.presentation.home.screens.AddBadgeScreen
import com.example.techbook.presentation.home.screens.HomeScreen
import com.example.techbook.presentation.home.screens.ProfileScreen
import com.example.techbook.ui.theme.TechBookSocialMediaForProgrammersTheme
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {
    private val auth by lazy {
        Firebase.auth
    }

    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TechBookSocialMediaForProgrammersTheme(darkTheme = false) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()

                    val imageList by viewModel.imageList
                    val user by viewModel.user
                    val allBadges by viewModel.badges
                    val allBadgesListByCollege by viewModel.badgeListFromCollege

                    Scaffold(
                        bottomBar = { NavigationBar(navController = navController) }
                    ) { padding ->

                        val isError by viewModel.uiState

                        if (isError.isLoading) {

                            LoadingDialog()
                        }
                        if (isError.isError) {
                            ErrorDialog(isError.message) {
                                viewModel.setIsError(isError = false)
                            }
                        }
                        NavHost(
                            navController = navController,
                            startDestination = Routes.Home.route
                        ) {

                            composable(Routes.Home.route) {

                                if (user is Resource.Success && user.data != null) {
                                    HomeScreen(
                                        user = user.data!!,
                                        allBadgesListByCollege.data,
                                        padding
                                    ) { collegeName ->
                                        viewModel.searchBadgesByCollege(collegeName)
                                    }
                                }
                                if (user is Resource.Loading) {
                                    LoadingDialog()
                                }
                                if (user is Resource.Error) {
                                    ErrorDialog(user.message.toString()) {
                                        viewModel.setIsError(isError = false)
                                    }
                                }
                            }

                            composable(Routes.Profile.route) {

                                if (user is Resource.Success && user.data != null) {
                                    ProfileScreen(user = user.data!!, allBadges.data, padding)
                                }
                                if (user is Resource.Loading) {
                                    LoadingDialog()
                                }
                                if (user is Resource.Error) {
                                    ErrorDialog(user.message.toString()) {
                                        viewModel.setIsError(isError = false)
                                    }
                                }

                            }
                            composable(Routes.AddBadge.route) {

                                AddBadgeScreen(imageUrlList = imageList, onImageSelected = {
                                    val bitmap = if (Build.VERSION.SDK_INT < 28) {
                                        MediaStore.Images
                                            .Media.getBitmap(this@HomeActivity.contentResolver, it)
                                    } else {
                                        val source = ImageDecoder
                                            .createSource(contentResolver, it)
                                        ImageDecoder.decodeBitmap(source)
                                    }
                                    viewModel.uploadImage(bitmap)

                                }, padding = padding) { badge ->
                                    viewModel.addBadge(badge) {
                                        navController.navigate(Routes.Home.route){
                                            popUpTo(Routes.Home.route)
                                        }
                                    }
                                }
                            }


                        }
                    }
                }
            }
        }
    }
}
