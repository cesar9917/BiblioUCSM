package com.cesar.biblioucsm.navigation

// Definición de rutas de navegación de la app
sealed class Screen(val route: String) {
    object Login : Screen("login") // Pantalla de login
    object Catalog : Screen("catalog") // Pantalla de catalogo de libros
    object Account : Screen("account")  // Pantalla de cuenta de usuario

    // Pantalla de detalle de libro con parámetro dinámico
    object Detail : Screen("detail/{bookId}") {
        // Genera la ruta con el ID real del libro
        fun createRoute(bookId: Int) = "detail/$bookId"
    }
}