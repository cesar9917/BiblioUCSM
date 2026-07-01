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
        // Organizamos todo verticalmente: Buscador arriba, Lista/Cargando abajo
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // 🔍 Barra de búsqueda interactiva
            OutlinedTextField(
                value = viewModel.textoBusqueda,
                onValueChange = { nuevoTexto -> viewModel.textoBusqueda = nuevoTexto },
                label = { Text("Buscar libro por título...") },
                placeholder = { Text("Ej. Android, Kotlin...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                singleLine = true
            )

            if (viewModel.cargando) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(viewModel.librosFiltrados) { libro ->
                        Card(modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(text = libro.titulo, style = MaterialTheme.typography.titleMedium)
                                Text(text = "Autor: ${libro.autor}", style = MaterialTheme.typography.bodyMedium)
                                Text(
                                    text = if (libro.disponible == 1) "Disponible" else "Prestado",
                                    color = if (libro.disponible == 1) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}