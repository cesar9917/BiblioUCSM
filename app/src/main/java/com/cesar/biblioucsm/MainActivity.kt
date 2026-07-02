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

// Actividad principal de la aplicación (entry point)
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Obtener instancia del servicio API configurado con Retrofit
        val apiService = RetrofitClient.apiService

        // 2. Inicialización de repositorios (capa de datos)
        val userRepository = UserRepository(apiService)
        val bookRepository = BookRepository(apiService)

        // 3. Inicialización de ViewModels con sus respectivos repositorios
        val loginViewModel = LoginViewModel(userRepository)
        val catalogViewModel = CatalogViewModel(bookRepository)

        // 4. Definición del contenido UI usando Jetpack Compose
        setContent {

            // Controlador de navegación entre pantallas
            val navController = rememberNavController()

            // 5. Se envían los ViewModels al grafo de navegación
            NavGraph(
                navController = navController,
                loginViewModel = loginViewModel,
                catalogViewModel = catalogViewModel
            )
        }
    }
}