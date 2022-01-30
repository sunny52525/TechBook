package com.example.techbook.presentation.auth.component


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.techbook.ui.theme.Dimens.grid_1
import com.example.techbook.ui.theme.Orange200
import com.example.techbook.ui.theme.poppins

@ExperimentalMaterialApi
@Composable
fun ExtendedButton(
    textColor: Color,
    backgroundColor: Color,
    text: String,
    modifier: Modifier,
    borderStroke: Dp = 0.dp,
    borderColor: Color = Orange200,
    cornerRadius: Int = 15,
    leftIcon: Boolean = false,
    leftIconTint: Color = Color.Black,
    rightIcon: Boolean = false,
    rightIconTint: Color = Color.Black,
    onClick: () -> Unit
) {


    Card(
        onClick = onClick, backgroundColor = backgroundColor,
        modifier = modifier,
        border = BorderStroke(borderStroke, borderColor),
        shape = RoundedCornerShape(cornerRadius.dp),
        role = Role.Button,

        ) {

        Box() {


            if (leftIcon) {
                Icon(
                    imageVector = Icons.Filled.ArrowBackIosNew,
                    contentDescription = "Back",
                    modifier = Modifier.align(Alignment.CenterStart),
                    tint = leftIconTint
                )
            }
            Text(
                text = text, color = textColor, textAlign = TextAlign.Center,
                fontFamily = poppins,
                fontWeight = FontWeight.Normal,
                fontSize = 24.sp,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(vertical = grid_1)

            )

            if (rightIcon) {
                Icon(
                    imageVector = Icons.Filled.ArrowBackIosNew,
                    contentDescription = "Back",
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .rotate(-180f),
                    tint = rightIconTint

                )
            }

        }
    }
}

@ExperimentalMaterialApi
@Preview
@Composable
fun ExtendedButtonPreview() {
    ExtendedButton(
        textColor = Orange200,
        backgroundColor = Color.White,
        text = "Next",
        modifier = Modifier.fillMaxWidth(),
        borderColor = Orange200,
        borderStroke = 1.dp,
        cornerRadius = 4
    ) {
        //do something
    }

}