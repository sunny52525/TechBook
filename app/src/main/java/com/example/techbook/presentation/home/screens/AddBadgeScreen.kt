package com.example.techbook.presentation.home.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.techbook.presentation.home.components.MoreDetails
import com.example.techbook.presentation.home.components.TypeOfBadgeSelector
import com.example.techbook.ui.theme.Dimens


@Composable
fun AddBadgeScreen() {


    var showBadge by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("Select Badge type") }
    var otherBadge by remember { mutableStateOf("") }

    var moreDetails by remember { mutableStateOf("") }


    Column(
        Modifier
            .verticalScroll(rememberScrollState())
            .padding(start = Dimens.grid_1_5, end = Dimens.grid_1),
        verticalArrangement = Arrangement.spacedBy(Dimens.grid_1_5)
    ) {

        TypeOfBadgeSelector(dropDownMenu = showBadge, onDropDownChange = {
            showBadge = showBadge.not()
        }, onDropDownClick = {
            name = it
        }, type = name,
            otherBadge = otherBadge
        ) {
            otherBadge = it
        }


        MoreDetails(moreDetail = moreDetails, onMoreDetailChange = { moreDetail ->
            moreDetails = moreDetail
        })
    }

}

