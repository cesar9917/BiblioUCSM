package com.cesar.biblioucsm.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Catalog : Screen("catalog")
    object Account : Screen("account")
}