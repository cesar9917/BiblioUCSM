package com.cesar.biblioucsm.ui.screens.catalog

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.cesar.biblioucsm.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogScreen(
    viewModel: CatalogViewModel,
    navController: NavController
    ) {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.cargarCatalogo()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Biblioteca UCSM - Catálogo") },
                actions = {
                    // 2. Botón que abre el menú
                    IconButton(onClick = { expanded = true }) {
                        Icon(Icons.Filled.AccountCircle, contentDescription = "Perfil")
                    }

                    // 3. El Menú desplegable
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Ver Cuenta") },
                            leadingIcon = { Icon(Icons.Filled.Person, contentDescription = null) },
                            onClick = {
                                expanded = false
                                // TODO: Navegar a pantalla de cuenta
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Cerrar Sesión") },
                            leadingIcon = { Icon(Icons.Filled.ExitToApp, contentDescription = null) },
                            onClick = {
                                expanded = false
                                viewModel.cerrarSesion(context)

                                // Ahora navController ya es reconocido
                                navController.navigate(Screen.Login.route) {
                                    popUpTo(Screen.Catalog.route) { inclusive = true }
                                }
                            }
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Barra de búsqueda
            OutlinedTextField(
                value = viewModel.textoBusqueda,
                onValueChange = { nuevoTexto -> viewModel.textoBusqueda = nuevoTexto },
                label = { Text("Buscar libro por título...") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Filtros
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = viewModel.filtroSeleccionado == FiltroDisponibilidad.TODOS,
                    onClick = { viewModel.filtroSeleccionado = FiltroDisponibilidad.TODOS },
                    label = { Text("Todos") }
                )
                FilterChip(
                    selected = viewModel.filtroSeleccionado == FiltroDisponibilidad.DISPONIBLES,
                    onClick = { viewModel.filtroSeleccionado = FiltroDisponibilidad.DISPONIBLES },
                    label = { Text("Disponibles") }
                )
                FilterChip(
                    selected = viewModel.filtroSeleccionado == FiltroDisponibilidad.PRESTADOS,
                    onClick = { viewModel.filtroSeleccionado = FiltroDisponibilidad.PRESTADOS },
                    label = { Text("Prestados") }
                )
            }

            if (viewModel.cargando) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
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