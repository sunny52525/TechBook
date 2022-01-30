package com.example.techbook.presentation.home.navigation

sealed class Routes(val route:String){
    object Home : Routes("/")
    object Profile : Routes("/profile")
    object AddBadge : Routes("/addBadge")

}
