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

@Composable
fun BookDetailScreen(
    navController: NavController,
    bookId: Int, // 🆕 Cambiado para coincidir con la ruta de tu Screen.kt
    viewModel: DetailViewModel
) {
    // 🆕 Se dispara cuando cambia el id en inglés
    LaunchedEffect(bookId) {
        viewModel.loadBookDetail(bookId)
    }

    // 🆕 Estados del ViewModel actualizados a inglés (isLoading)
    if (viewModel.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = androidx.compose.ui.Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else if (viewModel.book != null) {
        val book = viewModel.book!!
        Column(modifier = Modifier.padding(16.dp).verticalScroll(rememberScrollState())) {
            AsyncImage(
                // 🆕 Propiedades del modelo Book adaptadas (.imageUrl, .title, .author)
                model = book.imageUrl,
                contentDescription = "Portada",
                modifier = Modifier.fillMaxWidth().height(300.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = book.title, style = MaterialTheme.typography.headlineMedium)
            Text(text = "Autor: ${book.author}", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Descripción", style = MaterialTheme.typography.titleSmall)
            Text(
                text = book.descripcion ?: "Sin descripción disponible",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}