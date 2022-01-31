package com.example.techbook.presentation.home.components

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.techbook.common.Constants
import com.example.techbook.presentation.home.navigation.Routes
import com.example.techbook.ui.theme.Orange200


@Composable
fun NavigationBar(navController: NavHostController) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    BottomNavigation(
        backgroundColor = Color.White
    ) {
        Constants.BottomNavItems.forEach { navItem ->
            BottomNavigationItem(
                selected = currentRoute == navItem.route,
                onClick = {

                    if (currentRoute != navItem.route)
                        navController.navigate(navItem.route){
                            popUpTo(Routes.Home.route)
                        }
                },

                icon = {
                    Icon(
                        imageVector = navItem.icon,
                        contentDescription = navItem.label,
                        tint = Orange200
                    )
                },

                label = {
                    Text(text = navItem.label, color = Orange200)
                },
                alwaysShowLabel = false
            )
        }

    }
}