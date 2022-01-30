package com.example.techbook.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import com.example.techbook.ui.theme.Dimens.grid_2


@Composable
fun ErrorDialog(message: String,onDismiss: () -> Unit) {
    AlertDialog(
        text = {
            Text(text = message, color = Black, modifier = Modifier.padding(grid_2))

        }, onDismissRequest =onDismiss,
        title = {
            Text(text = "Message", color = Black, modifier = Modifier.padding(grid_2))
        },
        buttons = {

        }
    )
}