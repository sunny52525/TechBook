package com.example.techbook.presentation.auth.component

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.techbook.ui.theme.Orange700

@Preview
@Composable
fun LogoText(
    modifier: Modifier = Modifier,
    color: Color = Orange700,
){

    Text(
        text = "TechBook",
        color = color,
        fontSize = 55.sp,
        textAlign = TextAlign.Start,
        modifier = modifier,
        fontWeight = FontWeight.Bold
    )

}