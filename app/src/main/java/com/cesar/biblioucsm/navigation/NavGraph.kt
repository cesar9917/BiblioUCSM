package com.cesar.biblioucsm.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.cesar.biblioucsm.ui.screens.catalog.CatalogScreen
import com.cesar.biblioucsm.ui.screens.catalog.CatalogViewModel
import com.cesar.biblioucsm.ui.screens.login.LoginScreen
import com.cesar.biblioucsm.ui.screens.login.LoginViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    loginViewModel: LoginViewModel,
    catalogViewModel: CatalogViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        // Ruta 1: Login
        composable(Screen.Login.route) {
            LoginScreen(
                viewModel = loginViewModel,
                onNavegarAlCatalogo = {
                    // Al loguearse con éxito, navega al catálogo y borra el Login del historial
                    navController.navigate(Screen.Catalog.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        // Ruta 2: Catálogo
        composable(Screen.Catalog.route) {
            CatalogScreen(viewModel = catalogViewModel)
        }
    }
}