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

        composable(Screen.Catalog.route) {
            CatalogScreen(
                viewModel = catalogViewModel,
                navController = navController
            )
        }

        composable(Screen.Account.route) {
            AccountScreen(navController = navController)
        }
    }
}