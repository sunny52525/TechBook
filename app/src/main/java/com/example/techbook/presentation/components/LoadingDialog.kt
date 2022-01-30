package com.example.techbook.presentation.components

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun LoadingDialog() {
    Dialog(
        onDismissRequest = {

        }, properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,

        )
    ) {
        CircularProgressIndicator()
    }
}