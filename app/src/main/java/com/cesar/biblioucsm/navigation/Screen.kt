package com.cesar.biblioucsm.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Catalog : Screen("catalog")
    object Account : Screen("account")


    sealed class Screen(val route: String) {
        object Login : Screen("login")
        object Catalog : Screen("catalog")
        object Account : Screen("account")
        object Detail : Screen("detalle") { // Debe coincidir con la base de la ruta
            fun createRoute(libroId: Int) = "detalle/$libroId"
        }
    }

    object Detail : Screen("detalle") {
        fun createRoute(libroId: Int) = "detalle/$libroId"
    }
}