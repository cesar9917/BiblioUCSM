package com.cesar.biblioucsm.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.cesar.biblioucsm.ui.screens.catalog.CatalogScreen
import com.cesar.biblioucsm.ui.screens.catalog.CatalogViewModel
import com.cesar.biblioucsm.ui.screens.login.LoginScreen
import com.cesar.biblioucsm.ui.screens.login.LoginViewModel
import com.cesar.biblioucsm.ui.screens.account.AccountScreen

// Grafo de navegación principal de la app
@Composable
fun NavGraph(
    navController: NavHostController,
    loginViewModel: LoginViewModel,
    catalogViewModel: CatalogViewModel
) {
    // Contenedor principal de navegacion
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        // Pantalla de login
        composable(Screen.Login.route) {
            LoginScreen(
                viewModel = loginViewModel,
                onNavigateToCatalog = {
                    navController.navigate(Screen.Catalog.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }
        // Pantalla de catálogo de libros
        composable(Screen.Catalog.route) {
            CatalogScreen(
                viewModel = catalogViewModel,
                navController = navController
            )
        }
        // Pantalla de cuenta de usuario
        composable(Screen.Account.route) {
            AccountScreen(navController = navController)
        }
    }
}