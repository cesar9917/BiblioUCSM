package com.cesar.biblioucsm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.cesar.biblioucsm.data.network.ApiService
import com.cesar.biblioucsm.data.repository.LibroRepository
import com.cesar.biblioucsm.navigation.NavGraph
import com.cesar.biblioucsm.ui.screens.catalog.CatalogViewModel
import com.cesar.biblioucsm.ui.theme.BiblioUCSMTheme
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicialización de la arquitectura de datos (Retrofit -> Repository -> ViewModel)
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)
        val repository = LibroRepository(apiService)
        val viewModel = CatalogViewModel(repository)

        setContent {
            BiblioUCSMTheme {
                // 1. Creamos el estado del controlador de navegación de Jetpack Compose
                val navController = rememberNavController()

                // 2. Cargamos nuestro mapa de rutas centralizado
                NavGraph(
                    navController = navController,
                    catalogViewModel = viewModel
                )
            }
        }
    }
}