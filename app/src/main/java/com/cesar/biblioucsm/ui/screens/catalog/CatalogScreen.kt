package com.cesar.biblioucsm.ui.screens.catalog

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogScreen(viewModel: CatalogViewModel) {
    // Ejecuta la carga de datos al entrar a la pantalla
    LaunchedEffect(Unit) {
        viewModel.cargarCatálogo()
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Biblioteca UCSM - Catálogo") })
        }
    ) { paddingValues ->
        if (viewModel.cargando) {
            Box(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(viewModel.listaLibros) { libro ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = libro.titulo, style = MaterialTheme.typography.titleMedium)
                            Text(text = "Autor: ${libro.autor}", style = MaterialTheme.typography.bodyMedium)
                            Text(
                                text = if (libro.disponible) "Disponible" else "Prestado",
                                color = if (libro.disponible) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            }
        }
    }
}