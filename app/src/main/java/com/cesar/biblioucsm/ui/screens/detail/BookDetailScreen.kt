package com.cesar.biblioucsm.ui.screens.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage

// Pantalla de detalle de un libro seleccionado
@Composable
fun BookDetailScreen(
    navController: NavController,
    bookId: Int,
    viewModel: DetailViewModel
) {

    // Se ejecuta cuando cambia el ID del libro
    LaunchedEffect(bookId) {
        viewModel.loadBookDetail(bookId) // carga detalle del libro
    }

    // Si el ViewModel está cargando datos
    if (viewModel.isLoading) {

        // Pantalla de carga centrada
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = androidx.compose.ui.Alignment.Center
        ) {
            CircularProgressIndicator() // indicador de carga
        }

        // Si ya se cargó el libro y existe información
    } else if (viewModel.book != null) {

        // Se obtiene el libro cargado
        val book = viewModel.book!!

        // Contenedor principal con scroll vertical
        Column(
            modifier = Modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {

            // Imagen del libro
            AsyncImage(
                model = book.imageUrl,
                contentDescription = "Portada",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Título del libro
            Text(
                text = book.title,
                style = MaterialTheme.typography.headlineMedium
            )

            // Autor del libro
            Text(
                text = "Autor: ${book.author}",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            // TItulo de sección descripciOn
            Text(
                text = "Descripción",
                style = MaterialTheme.typography.titleSmall
            )

            // DescripciOn del libro con control de null
            Text(
                text = book.descripcion ?: "Sin descripción disponible",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}