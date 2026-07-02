package com.cesar.biblioucsm.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Catalog : Screen("catalog")
    object Account : Screen("account")

    object Detail : Screen("detail/{bookId}") {
        fun createRoute(bookId: Int) = "detail/$bookId"
    }
}