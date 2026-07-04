package com.cesar.biblioucsm.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.cesar.biblioucsm.data.repository.BookRepository
import com.cesar.biblioucsm.data.repository.UserRepository
import com.cesar.biblioucsm.ui.screens.catalog.CatalogScreen
import com.cesar.biblioucsm.ui.screens.catalog.CatalogViewModel
import com.cesar.biblioucsm.ui.screens.catalog.CatalogViewModelFactory
import com.cesar.biblioucsm.ui.screens.login.LoginScreen
import com.cesar.biblioucsm.ui.screens.login.LoginViewModel
import com.cesar.biblioucsm.ui.screens.login.LoginViewModelFactory
import com.cesar.biblioucsm.ui.screens.account.AccountScreen

// Grafo de navegación principal de la app
@Composable
fun NavGraph(
    navController: NavHostController,
    userRepository: UserRepository,
    bookRepository: BookRepository
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(Screen.Login.route) {
            val loginViewModel: LoginViewModel = viewModel(
                factory = LoginViewModelFactory(userRepository)
            )
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
            val catalogViewModel: CatalogViewModel = viewModel(
                factory = CatalogViewModelFactory(bookRepository)
            )
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