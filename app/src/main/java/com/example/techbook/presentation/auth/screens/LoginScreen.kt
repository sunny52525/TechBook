package com.example.techbook.presentation.auth.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.techbook.common.noRippleClickable
import com.example.techbook.presentation.auth.component.ExtendedButton
import com.example.techbook.presentation.auth.component.ExtendedTextField
import com.example.techbook.presentation.auth.component.HeaderArt
import com.example.techbook.presentation.auth.component.LogoText
import com.example.techbook.presentation.components.CollegeDialog
import com.example.techbook.ui.theme.Orange200
import com.example.techbook.ui.theme.OrangeDark
import com.example.techbook.ui.theme.poppins


@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@Composable
fun LoginScreen(
    email: String,
    password: String,
    isSignIn: Boolean = true,
    name: String = "",
    dropDownExpanded: Boolean = false,
    onDropDownClicked: () -> Unit,
    year: String = "",
    college: String = "",
    onNameChanged: (String) -> Unit,
    onYearChanged: (String) -> Unit,
    onCollegeChanged: (String) -> Unit,
    verifySignIn: () -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onRegisterClicked: () -> Unit,
    onForgotPasswordClicked: () -> Unit,
) {
    val focusRequester = FocusRequester()
    val scroller = rememberScrollState()
    Box(
        Modifier
            .fillMaxSize()
            .verticalScroll(scroller)
            .background(Color.White)
            .animateContentSize()
    ) {

        HeaderArt()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .animateContentSize()

        ) {
            Spacer(modifier = Modifier.height(50.dp))
            LogoText(color = Color.White, modifier = Modifier.padding(start = 26.dp))

            Card(
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                backgroundColor = Color.White,
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    Modifier
                        .background(Color.White)
                        .fillMaxHeight()
                        .padding(start = 26.dp, end = 26.dp, top = 100.dp),
                ) {

                    Spacer(modifier = Modifier.height(28.dp))


                    if (isSignIn.not()) {
                        ExtendedTextField(
                            text = name,
                            icon = Icons.Filled.Person,
                            textColor = Color.Gray,
                            iconTint = Orange200,
                            placeHolder = "Name",
                            imeAction = ImeAction.Next,
                            onChange = onNameChanged,
                            backgroundColor = Color.White
                        ) {


                        }
                        Column(
                            modifier = Modifier
                                .padding(vertical = 26.dp)
                                .fillMaxWidth()
                                .clickable {
                                    onDropDownClicked()

                                },
                            horizontalAlignment = Alignment.CenterHorizontally,

                            ) {
                            Text(
                                text = college,
                                modifier = Modifier
                                    .padding(start = 26.dp)
                                    .height(44.dp)

                                    .fillMaxWidth(), textAlign = TextAlign.Start
                            )
                            Divider(color = Orange200)
                        }

                        if (dropDownExpanded) {
                            CollegeDialog(onDismiss = { onDropDownClicked() }) {
                                onDropDownClicked()
                                onCollegeChanged(it)
                            }
                        }


                        ExtendedTextField(
                            text = year,
                            icon = Icons.Filled.Person,
                            textColor = Color.Gray,
                            iconTint = Orange200,
                            placeHolder = "Year(eg. 2020-2024)",
                            imeAction = ImeAction.Next,
                            onChange = onYearChanged,
                            backgroundColor = Color.White
                        ) {


                        }

                    }

                    ExtendedTextField(
                        text = email,
                        icon = Icons.Filled.Person,
                        textColor = Color.Gray,
                        iconTint = Orange200,
                        placeHolder = "Email",
                        imeAction = ImeAction.Next,
                        onChange = onEmailChange,
                        backgroundColor = Color.White
                    ) {


                    }

                    ExtendedTextField(
                        text = password,
                        icon = Icons.Filled.Lock,
                        textColor = Color.Gray,

                        iconTint = Orange200,

                        placeHolder = "Password",
                        imeAction = ImeAction.Done,
                        isPasswordField = true,
                        modifier = Modifier.focusRequester(focusRequester),
                        onChange = onPasswordChange, backgroundColor = Color.White


                    ) {
                        // TODO: 7/23/2021
                    }


                    Text(
                        text = "Forgot Password?",
                        fontWeight = FontWeight.Normal,
                        fontFamily = poppins,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                            .noRippleClickable(onClick = onForgotPasswordClicked),
                        textAlign = TextAlign.End,
                        fontSize = 18.sp,
                        color = Color.Gray,


                        )

                    Spacer(modifier = Modifier.height(43.dp))

                    ExtendedButton(
                        textColor = Color.White,
                        backgroundColor = OrangeDark,
                        cornerRadius = 4,
                        text = isSignIn.getCorrectAuthString(),
                        modifier = Modifier.fillMaxWidth(), onClick = verifySignIn,

                        )

                    Spacer(modifier = Modifier.height(58.dp))

                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                        Divider(
                            color = Color.Gray, thickness = 1.dp, modifier = Modifier
                                .fillMaxWidth(0.4f)
                                .weight(1f)
                                .align(Alignment.CenterVertically)
                        )
                        Text(
                            text = "OR",
                            Modifier
                                .padding(horizontal = 13.dp)
                                .align(Alignment.CenterVertically),
                            color = Color.Gray,
                            fontFamily = poppins,
                            fontSize = 18.sp
                        )
                        Divider(
                            color = Color.Gray, thickness = 1.dp, modifier = Modifier
                                .fillMaxWidth(0.4f)
                                .weight(1f)
                                .align(Alignment.CenterVertically)
                        )

                    }

                    Spacer(modifier = Modifier.height(50.dp))

                    Text(
                        text = "Don't have an account?",
                        fontWeight = FontWeight.Bold,
                        fontFamily = poppins,
                        fontSize = 18.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = Orange200
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    ExtendedButton(
                        textColor = Color.White,
                        backgroundColor = OrangeDark,
                        text = isSignIn.not().getCorrectAuthString(),
                        modifier = Modifier.fillMaxWidth(), onClick = onRegisterClicked,
                        cornerRadius = 4
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }

    }

}

fun Boolean.getCorrectAuthString(): String {
    return if (this) "Sign In" else "Sign Up"
}


@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Preview
@Composable
fun Login() {

    LoginScreen(
        email = "Kumarsunny3232@gmail.com",
        password = "test",
        verifySignIn = { /*TODO*/ },
        onEmailChange = {},
        onPasswordChange = {},
        onRegisterClicked = {},
        isSignIn = false,
        onNameChanged = {},
        onYearChanged = {},
        onCollegeChanged = {},
        onForgotPasswordClicked = {},
        name = "",
        dropDownExpanded = false,
        onDropDownClicked = {},
        year = "",
        college = "",
    )
}