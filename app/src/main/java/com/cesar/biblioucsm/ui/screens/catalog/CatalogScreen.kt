package com.cesar.biblioucsm.ui.screens.catalog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.cesar.biblioucsm.data.model.Libro
import com.cesar.biblioucsm.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogScreen(
    viewModel: CatalogViewModel,
    navController: NavController
) {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }

    // Estado para el panel de detalles
    var libroSeleccionado by remember { mutableStateOf<Libro?>(null) }

    // CORRECCIÓN AQUÍ:
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    LaunchedEffect(Unit) {
        viewModel.cargarCatalogo()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Biblioteca UCSM - Catálogo") },
                actions = {
                    IconButton(onClick = { expanded = true }) {
                        Icon(Icons.Filled.AccountCircle, contentDescription = "Perfil")
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Ver Cuenta") },
                            leadingIcon = { Icon(Icons.Filled.Person, contentDescription = null) },
                            onClick = {
                                expanded = false
                                navController.navigate(Screen.Account.route)
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Cerrar Sesión") },
                            leadingIcon = { Icon(Icons.Filled.ExitToApp, contentDescription = null) },
                            onClick = {
                                expanded = false
                                viewModel.cerrarSesion(context)
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
            OutlinedTextField(
                value = viewModel.textoBusqueda,
                onValueChange = { nuevoTexto -> viewModel.textoBusqueda = nuevoTexto },
                label = { Text("Buscar libro por título...") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Filtros
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FilterChip(selected = viewModel.filtroSeleccionado == FiltroDisponibilidad.TODOS, onClick = { viewModel.filtroSeleccionado = FiltroDisponibilidad.TODOS }, label = { Text("Todos") })
                FilterChip(selected = viewModel.filtroSeleccionado == FiltroDisponibilidad.DISPONIBLES, onClick = { viewModel.filtroSeleccionado = FiltroDisponibilidad.DISPONIBLES }, label = { Text("Disponibles") })
                FilterChip(selected = viewModel.filtroSeleccionado == FiltroDisponibilidad.PRESTADOS, onClick = { viewModel.filtroSeleccionado = FiltroDisponibilidad.PRESTADOS }, label = { Text("Prestados") })
            }

            if (viewModel.cargando) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(viewModel.librosFiltrados) { libro ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { libroSeleccionado = libro } // Al hacer clic abrimos el panel
                        ) {
                            Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
                                AsyncImage(
                                    model = libro.imagenUrl,
                                    contentDescription = "Portada",
                                    modifier = Modifier.size(80.dp).padding(end = 16.dp)
                                )
                                Column {
                                    Text(text = libro.titulo, style = MaterialTheme.typography.titleMedium)
                                    Text(text = "Autor: ${libro.autor}", style = MaterialTheme.typography.bodyMedium)
                                }
                            }
                        }
                    }
                }
            }
        }

        // --- Panel de Detalles que se despliega al seleccionar un libro ---
        libroSeleccionado?.let { libro ->
            ModalBottomSheet(
                onDismissRequest = { libroSeleccionado = null },
                sheetState = sheetState
            ) {
                Column(modifier = Modifier.padding(16.dp).padding(bottom = 32.dp)) {
                    AsyncImage(
                        model = libro.imagenUrl,
                        contentDescription = "Portada",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp),
                        contentScale = ContentScale.Fit
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = libro.titulo, style = MaterialTheme.typography.headlineSmall)
                    Text(text = "Autor: ${libro.autor}", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Descripción", style = MaterialTheme.typography.titleSmall)
                    Text(text = libro.descripcion ?: "Sin descripción disponible", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}