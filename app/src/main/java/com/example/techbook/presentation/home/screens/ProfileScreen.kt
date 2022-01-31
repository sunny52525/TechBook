package com.example.techbook.presentation.home.screens

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.rememberImagePainter
import com.example.techbook.common.Constants.DUMMY_IMAGE
import com.example.techbook.common.ExtensionFunctions.showToast
import com.example.techbook.domain.model.Badge
import com.example.techbook.domain.model.UserModel
import com.example.techbook.presentation.auth.screens.AuthActivity
import com.example.techbook.presentation.home.components.Header
import com.example.techbook.presentation.home.components.ProfileInfo
import com.example.techbook.ui.theme.Dimens.grid_2
import com.example.techbook.ui.theme.Orange200
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProfileScreen(user: UserModel, data: List<Badge>?, paddingValues: PaddingValues) {

    var showError by remember { mutableStateOf(false) }
    var showDialog by remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current as Activity

    Column(
        Modifier
            .fillMaxSize()
            .padding(grid_2)
            .padding(paddingValues)
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Header(user = user, onBadgeClick = {
            context.showToast("Badge clicked, to be implemented")
        }, data)


        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            Card(onClick = {
                showDialog = true

            }, modifier = Modifier.height(50.dp)) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(text = "All Badges")
                }
            }

            ProfileInfo(title = "Name", value = user.name)
            ProfileInfo(title = "Email", value = user.email)
            ProfileInfo(title = "College", value = user.college)
            ProfileInfo(title = "Referral points", value = user.referPoint.toString())


            Card(onClick = {
                Intent(context, AuthActivity::class.java).apply {
                    Firebase.auth.signOut()
                    context.startActivity(this)
                }
            }, modifier = Modifier.height(50.dp)) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(text = "Logout")
                }
            }
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



    if (showDialog) {
        Dialog(onDismissRequest = { showDialog = false }) {
            LazyColumn {

                data?.forEach {
                    item {
                        BadgeCard(badge = it)
                    }
                }

            }
        }
    }
}


@Preview
@Composable
fun BadgeCard(badge: Badge = Badge()) {

    val expanded by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .border(1.dp, color = Orange200)
            .background(Color.White)
            .padding(10.dp)
            .fillMaxWidth()
    ) {
        Row {
            Image(
                painter = rememberImagePainter(DUMMY_IMAGE),
                contentDescription = "",
                modifier = Modifier
                    .clip(CircleShape)
                    .size(50.dp)
            )

            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(text = badge.name)
                Text(text = badge.typeOfBadge)
                Text(text = badge.time.toString())
            }
        }


        Text(text = badge.moreInfo)

        Row(Modifier.horizontalScroll(rememberScrollState())) {
            badge.imageUrls.forEach { imageUrl ->
                imageUrl?.let {
                    Image(
                        painter = rememberImagePainter(imageUrl),
                        contentDescription = "",
                        modifier = Modifier
                            .size(100.dp)
                            .padding(10.dp)
                    )
                }

            }
        }

    }
}
