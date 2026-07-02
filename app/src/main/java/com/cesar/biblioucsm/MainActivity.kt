package com.cesar.biblioucsm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.cesar.biblioucsm.data.network.RetrofitClient
import com.cesar.biblioucsm.data.repository.BookRepository
import com.cesar.biblioucsm.data.repository.UserRepository
import com.cesar.biblioucsm.navigation.NavGraph
import com.cesar.biblioucsm.ui.screens.catalog.CatalogViewModel
import com.cesar.biblioucsm.ui.screens.login.LoginViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Obtener la instancia del ApiService (Retrofit)
        val apiService = RetrofitClient.apiService

        // 2. Inicializar ambos Repositorios (Nombres corregidos en camelCase)
        val userRepository = UserRepository(apiService)
        val bookRepository = BookRepository(apiService)

        // 3. Inicializar ambos ViewModels pasándoles su respectivo repositorio
        val loginViewModel = LoginViewModel(userRepository)
        val catalogViewModel = CatalogViewModel(bookRepository)

        setContent {
            val navController = rememberNavController()

            // 4. Pasar los ViewModels configurados y limpios al NavGraph
            NavGraph(
                navController = navController,
                loginViewModel = loginViewModel,
                catalogViewModel = catalogViewModel
            )
        }
    }
}