package com.cesar.biblioucsm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.cesar.biblioucsm.data.network.RetrofitClient
import com.cesar.biblioucsm.data.repository.LibroRepository
import com.cesar.biblioucsm.data.repository.UserRepository
import com.cesar.biblioucsm.navigation.NavGraph
import com.cesar.biblioucsm.ui.screens.catalog.CatalogViewModel
import com.cesar.biblioucsm.ui.screens.login.LoginViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Obtener la instancia del ApiService (Retrofit)
        val apiService = RetrofitClient.apiService

        // 2. Inicializar ambos Repositorios
        val userRepository = UserRepository(apiService)
        val LibroRepository = LibroRepository(apiService)

        // 3. Inicializar ambos ViewModels pasándoles su repositorio
        val loginViewModel = LoginViewModel(userRepository)
        val catalogViewModel = CatalogViewModel(LibroRepository)

        setContent {
            val navController = rememberNavController()

            // 4. Pasar el nuevo loginViewModel al NavGraph (Línea 37 ⚡)
            NavGraph(
                navController = navController,
                loginViewModel = loginViewModel,
                catalogViewModel = catalogViewModel
            )
        }
    }
}