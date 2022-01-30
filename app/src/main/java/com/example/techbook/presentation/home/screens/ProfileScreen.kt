package com.example.techbook.presentation.home.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.techbook.common.Resource
import com.example.techbook.domain.model.UserModel
import com.example.techbook.presentation.components.ErrorDialog
import com.example.techbook.presentation.components.LoadingDialog

@Composable
fun ProfileScreen(user: Resource<UserModel?>) {

    when (user) {
        is Resource.Error -> ErrorDialog(message = user.message.toString()) {

        }
        is Resource.Loading -> LoadingDialog()
        is Resource.Success -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = user.data?.name.toString())

            }
        }
    }

}