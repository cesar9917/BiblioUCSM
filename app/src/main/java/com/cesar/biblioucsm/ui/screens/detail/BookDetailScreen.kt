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
    libroId: Int,
    viewModel: DetailViewModel
) {
    LaunchedEffect(libroId) {
        viewModel.obtenerDetalle(libroId)
    }

    if (viewModel.cargando) {
        CircularProgressIndicator()
    } else if (viewModel.libro != null) {
        val libro = viewModel.libro!!
        Column(modifier = Modifier.padding(16.dp).verticalScroll(rememberScrollState())) {
            AsyncImage(
                model = libro.imagenUrl ?: "",
                contentDescription = "Portada",
                modifier = Modifier.fillMaxWidth().height(300.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = libro.titulo, style = MaterialTheme.typography.headlineMedium)
            Text(text = "Autor: ${libro.autor}", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Descripción", style = MaterialTheme.typography.titleSmall)
            Text(
                text = libro.descripcion ?: "Sin descripción disponible",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}