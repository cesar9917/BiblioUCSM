package com.cesar.biblioucsm.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login_screen")
    object Catalog : Screen("catalog_screen")
}