package com.example.ksb

enum class NavigationScreen{
    DATA_SCREEN,DATA_DETAILS
}
sealed class Screens(val route : String) {
    data object DATA_SCREEN: Screens(NavigationScreen.DATA_SCREEN.name)
    data object DATA_DETAILS: Screens(NavigationScreen.DATA_DETAILS.name)
}