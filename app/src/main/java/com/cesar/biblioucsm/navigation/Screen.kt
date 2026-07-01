package com.cesar.biblioucsm.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Catalog : Screen("catalog")
}