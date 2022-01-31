package com.example.techbook.presentation.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.techbook.common.Constants
import com.example.techbook.domain.model.Badge
import com.example.techbook.domain.model.UserModel
import com.example.techbook.ui.theme.Dimens.grid_2

@Composable
fun Header(user: UserModel, onBadgeClick: (Badge) -> Unit, badges: List<Badge>?) {

    Row(
        Modifier
            .border(color = Color.Black, width = 1.dp)
            .fillMaxWidth()) {
        Image(
            painter = rememberImagePainter(Constants.DUMMY_IMAGE),
            contentDescription = "",
            modifier = Modifier
                .clip(shape = CircleShape)
                .height(50.dp)
        )
        Column {
            Text(text = "Badges")

            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                badges?.forEach {
                    BadgeIcon(count = it.typeOfBadge.first(), onClick = {
                        onBadgeClick(it)
                    })
                }
            }
        }
    }

}

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun BadgeIcon(count: Char = 'A', onClick: () -> Unit = {}) {
    Card(
        shape = CircleShape,
        backgroundColor = getRandomColor(),
        modifier = Modifier.size(grid_2),
        onClick = onClick
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = count.toString()
            )
        }
    }

}


//get random vibrant color
fun getRandomColor(): Color {
    val r = (Math.random() * 255).toInt()
    val g = (Math.random() * 255).toInt()
    val b = (Math.random() * 255).toInt()
    return Color(r, g, b)
}


@Preview
@Composable
fun ProfileInfo(title: String = "Name", value: String = "Sunny") {

    Column {
        Text(text = title)
        Card {
            Text(text = value, fontSize = 20.sp)

        }
    }

}