package com.example.techbook.presentation.home.screens

import android.Manifest
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.techbook.common.isPermanentlyDenied
import com.example.techbook.domain.model.Badge
import com.example.techbook.presentation.home.components.AddImages
import com.example.techbook.presentation.home.components.MoreDetails
import com.example.techbook.presentation.home.components.TypeOfBadgeSelector
import com.example.techbook.ui.theme.Dimens
import com.example.techbook.ui.theme.Dimens.grid_2_5
import com.example.techbook.ui.theme.Orange200
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun AddBadgeScreen(
    imageUrlList: List<String?>,
    onImageSelected: (Uri) -> Unit,
    padding: PaddingValues,
    onBadgeAdd: (Badge) -> Unit,
) {


    val permissionState =
        rememberMultiplePermissionsState(permissions = listOf(Manifest.permission.READ_EXTERNAL_STORAGE))

    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        Log.d("", "AddBadgeScreen: uri: $uri")
        if (uri != null) {
            onImageSelected(uri)
        } else {
            Toast.makeText(context, "No image selected", Toast.LENGTH_SHORT).show()
        }
    }

    var showBadge by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("Select Badge type") }
    var otherBadge by remember { mutableStateOf("") }

    var moreDetails by remember { mutableStateOf("") }


    Column(
        Modifier
            .verticalScroll(rememberScrollState())
            .padding(start = Dimens.grid_1_5, end = Dimens.grid_1).padding(padding),
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

        AddImages(imageLinks = imageUrlList, onAddImageClicked = {

            if (imageUrlList.size < 5) {
                permissionState.permissions.forEach { perm ->
                    when (perm.permission) {
                        Manifest.permission.READ_EXTERNAL_STORAGE -> {
                            when {
                                perm.hasPermission -> {
                                    launcher.launch("image/*")
                                }
                                perm.shouldShowRationale -> {
                                    permissionState.launchMultiplePermissionRequest()
                                }
                                perm.isPermanentlyDenied() -> {
                                    Toast.makeText(
                                        context,
                                        "This Feature required Permission, ENable in in the settings",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }

                    }
                }
            } else {
                Toast.makeText(context, "You can add maximum 5 images", Toast.LENGTH_SHORT).show()
            }
        })


        OutlinedButton(
            onClick = {
                onBadgeAdd(
                    Badge(
                        moreInfo = moreDetails,
                        imageUrls = imageUrlList,
                        typeOfBadge = if (name == "Other") otherBadge else name
                    )
                )
            },
            colors = ButtonDefaults.outlinedButtonColors(
                backgroundColor = Color.White,


                ),

            modifier = Modifier.fillMaxWidth(),
            border = BorderStroke(1.dp, Orange200),
            shape = RoundedCornerShape(grid_2_5)
        ) {

            Text("Add Badge", color = Orange200, fontSize = 20.sp)

        }
    }

}

