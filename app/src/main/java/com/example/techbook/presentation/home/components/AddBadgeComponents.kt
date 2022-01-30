package com.example.techbook.presentation.home.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.techbook.ui.theme.Dimens.grid_1
import com.example.techbook.ui.theme.Dimens.grid_1_25
import com.example.techbook.ui.theme.Dimens.grid_1_5
import com.example.techbook.ui.theme.Dimens.grid_2
import com.example.techbook.ui.theme.Dimens.grid_5
import com.example.techbook.ui.theme.Orange200
import com.example.techbook.ui.theme.Orange500

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TypeOfBadgeSelector(
    dropDownMenu: Boolean = true,
    onDropDownChange: () -> Unit = {

    },
    onDropDownClick: (String) -> Unit = {},
    type: String = "",
    otherBadge: String,
    onOtherBadgeChange: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .animateContentSize()
    ) {

        Row(
            Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(grid_2),
            horizontalArrangement = Arrangement.spacedBy(grid_1)
        ) {

            Text(
                text = "Type of badge",
                modifier = Modifier.padding(grid_1),
                style = MaterialTheme.typography.body1
            )


            Box {

                Card(
                    onClick = { onDropDownChange() },
                    shape = RoundedCornerShape(grid_1_25),
                    modifier = Modifier
                        .height(
                            grid_5
                        )
                        .fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(text = type)
                    }

                }
                DropdownMenu(expanded = dropDownMenu, onDismissRequest = {
                    onDropDownChange()
                }) {

                    listOf(
                        "Skill",
                        "Award",
                        "Job",
                        "Internship",
                        "Course",
                        "Project",
                        "Other"
                    ).forEach {

                        DropdownMenuItem(
                            onClick = {
                                onDropDownChange()
                                onDropDownClick(it)
                            }
                        ) {
                            Text(text = it, color = Color.Black)
                        }
                    }
                }
            }

        }

        if (type == "Other") {
            OutlinedTextField(
                value = otherBadge,
                onValueChange = {
                    onOtherBadgeChange(it)
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(grid_1_25),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Gray,
                    backgroundColor = Color.White,
                    focusedIndicatorColor = Orange200,
                    unfocusedIndicatorColor = Orange500
                ),
                placeholder = {
                    Text(
                        text = "Enter Badge type",
                        color = Color.Black,
                        modifier = Modifier.alpha(0.5f)
                    )
                }
            )
        }
    }
}

@Preview
@Composable
fun MoreDetails(moreDetail: String = "", onMoreDetailChange: (String) -> Unit = {}) {
    Column {
        Text(text = "More Details(Max 1000 characters)", style = MaterialTheme.typography.body1)
        Spacer(modifier = Modifier.height(grid_1_5))
        OutlinedTextField(
            value = moreDetail,
            onValueChange = {
                if (it.length <= 1000)
                    onMoreDetailChange(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp),
            shape = RoundedCornerShape(grid_1_25),
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Gray,
                backgroundColor = Color.White,
                focusedIndicatorColor = Orange200,
                unfocusedIndicatorColor = Orange500
            ),
            placeholder = {
                Text(
                    text = "Enter more details",
                    color = Color.Black,
                    modifier = Modifier.alpha(0.5f)
                )
            }
        )
    }
}

