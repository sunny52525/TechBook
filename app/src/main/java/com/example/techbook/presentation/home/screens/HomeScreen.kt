package com.example.techbook.presentation.home.screens

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.techbook.common.Constants
import com.example.techbook.domain.model.Badge
import com.example.techbook.domain.model.UserModel
import com.example.techbook.ui.theme.Dimens
import com.example.techbook.ui.theme.Orange200
import com.example.techbook.ui.theme.Orange500
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    user: UserModel,
    data: List<Badge>?,
    padding: PaddingValues,
    onSearch: (String) -> Unit
) {

    var isInSearchMode by remember {
        mutableStateOf(false)
    }

    var searchQuery by remember {
        mutableStateOf("Search")
    }
    val context = LocalContext.current as Activity
    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(padding)
    ) {

        stickyHeader {
            Card(onClick = {
                isInSearchMode = !isInSearchMode
            }, modifier = Modifier.height(50.dp)) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = searchQuery,
                        style = MaterialTheme.typography.h6
                    )
                }
            }
        }
        data?.forEach {
            item {
                BadgeCard(it)
            }
        }

        if (data?.isEmpty() == true){
            item {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No badges found, Invite them to see their badges",
                        style = MaterialTheme.typography.h6
                    )

                    Card(onClick = {
                        val sendIntent: Intent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(
                                Intent.EXTRA_TEXT,
                                "${user.name} has sent you 10 referral points, Sign up to redeem them https://com.techbook.com/refer/${Firebase.auth.currentUser?.uid}"
                            )
                            type = "text/plain"
                        }
                        val shareIntent = Intent.createChooser(sendIntent, null)
                        context.startActivity(shareIntent)
                    }, modifier = Modifier.height(50.dp)) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Text(text = "Refer")
                        }
                    }
                }
            }
        }

    }

    if (isInSearchMode) {
        SearchCollege(onDismiss = { isInSearchMode = false }, onSearch = { collegeName ->
            searchQuery = collegeName
            isInSearchMode = false
            onSearch(collegeName)
        })
    }

}


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun SearchCollege(onDismiss: () -> Unit, onSearch: (String) -> Unit) {
    var query by remember {
        mutableStateOf("")
    }

    var college by remember {
        mutableStateOf("")
    }
    Dialog(onDismissRequest = onDismiss) {
        Column(
            Modifier
                .background(Color.White)
        ) {
            Text("Search College")

            Row {
                OutlinedTextField(
                    value = query,
                    onValueChange = {
                        query = it
                    },
                    modifier = Modifier.fillMaxWidth(0.7f),
                    singleLine = true,
                    shape = RoundedCornerShape(Dimens.grid_1_25),
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.Gray,
                        backgroundColor = Color.White,
                        focusedIndicatorColor = Orange200,
                        unfocusedIndicatorColor = Orange500
                    ),
                    placeholder = {
                        Text(
                            text = "Enter College name",
                            color = Color.Black,
                            modifier = Modifier.alpha(0.5f)
                        )
                    }
                )


            }



            LazyColumn(content = {
                stickyHeader {
                    Text(text = "Search Results")

                }
                items(filter(query)) {
                    Card(onClick = {
                        college = it
                        onSearch(college)
                    }) {
                        Text(text = it)
                    }
                }


            })

        }
    }
}


//filter array list from query
fun filter(query: String): List<String> {
    return Constants.collegeList.filter {
        it.contains(query, true)
    }
}

