package com.example.ksb

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ksb.datalist.domain.model.Post
import com.example.ksb.datalist.presenter.screen.DataDetailsPage
import com.example.ksb.datalist.presenter.screen.DataScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screens.DATA_SCREEN.route) {
        composable(route = Screens.DATA_SCREEN.route) {
            DataScreen(onItemClick = {
                navController.currentBackStackEntry?.savedStateHandle?.set("postData", it)
                navController.navigate(Screens.DATA_DETAILS.route)
            })
        }

        composable(route = Screens.DATA_DETAILS.route) {
            val postData =
                navController.previousBackStackEntry?.savedStateHandle?.get<Post>("postData")
            if (postData != null) {
                DataDetailsPage(postData)
            }
        }
    }
}
