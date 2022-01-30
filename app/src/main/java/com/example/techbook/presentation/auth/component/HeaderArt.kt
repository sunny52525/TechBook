package com.example.techbook.presentation.auth.component


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.techbook.ui.theme.Orange200
import com.example.techbook.ui.theme.OrangeInner1
import com.example.techbook.ui.theme.OrangeInner2
import com.example.techbook.ui.theme.OrangeInner3

@Preview
@Composable
fun HeaderArt() {


    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(Orange200), onDraw = {
            val canvasWidth = size.width
            val canvasHeight = size.height

            drawCircle(

                color = OrangeInner1,
                center = Offset(
                    x = canvasWidth,
                    y = canvasHeight
                ),
                radius = (3) * canvasHeight / 4,
            )
            drawCircle(
                color = OrangeInner2,
                center = Offset(
                    x = canvasWidth,
                    y = canvasHeight
                ),
                radius = (2) * canvasHeight / 4,
            )

            drawCircle(
                color = OrangeInner3,
                center = Offset(
                    x = canvasWidth,
                    y = canvasHeight
                ),
                radius = 1 * canvasHeight / 4,
            )
        })

}