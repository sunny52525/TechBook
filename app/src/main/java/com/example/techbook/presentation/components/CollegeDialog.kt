package com.example.techbook.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import com.example.techbook.common.Constants


@Composable
fun CollegeDialog(onDismiss: () -> Unit, onSelect: (String) -> Unit) {

    Dialog(onDismissRequest = onDismiss) {
        LazyColumn(Modifier.background(Color.White)) {
            items(Constants.collegeList) { college ->
                CollegeItem(college) {
                    onSelect(college)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun CollegeItem(name: String = "NIT", onClick: () -> Unit = {}) {
    Card(onClick = onClick, modifier = Modifier.fillMaxWidth()) {
        Text(text = name, textAlign = TextAlign.Start, style = MaterialTheme.typography.body1)
    }
}