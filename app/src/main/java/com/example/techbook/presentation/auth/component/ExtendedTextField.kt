package com.example.techbook.presentation.auth.component


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.techbook.ui.theme.Orange200

@ExperimentalComposeUiApi
@Composable
fun ExtendedTextField(
    modifier: Modifier = Modifier,
    text: String,
    icon: ImageVector,
    textColor: Color,
    iconTint: Color,
    isPasswordField: Boolean = false,
    placeHolder: String,
    imeAction: ImeAction,
    onChange: (String) -> Unit,
    backgroundColor: Color = Orange200,
    onComplete: () -> Unit,
) {

    val keyboardController = LocalSoftwareKeyboardController.current
    var passwordVisibility by remember { mutableStateOf(false) }

    TextField(
        value = text,
        onValueChange = {
            onChange(it)
        },
        leadingIcon = {
            Icon(
                imageVector = icon, contentDescription = "leadingIcon",
                tint = iconTint,
                modifier = Modifier.size(32.dp)
            )

        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = imeAction,
            autoCorrect = true,
        ),
        keyboardActions = KeyboardActions {
            keyboardController?.hide()
            onComplete()
        },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = textColor,
            unfocusedIndicatorColor = Orange200,
            disabledIndicatorColor = textColor,
            textColor = textColor,
            backgroundColor = backgroundColor,
            cursorColor = textColor,

            ),
        placeholder = {
            Text(
                text = placeHolder,
                fontSize = 18.sp,
                color = textColor,
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor),
        trailingIcon = {
            val image: ImageVector = if (passwordVisibility)
                Icons.Filled.Visibility
            else Icons.Filled.VisibilityOff
            if (isPasswordField) {
                IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                    Icon(imageVector = image, "")
                }
            }

        },
        visualTransformation = if (passwordVisibility || !isPasswordField) VisualTransformation.None else PasswordVisualTransformation(),
        singleLine = true,
        textStyle = TextStyle()
    )
}