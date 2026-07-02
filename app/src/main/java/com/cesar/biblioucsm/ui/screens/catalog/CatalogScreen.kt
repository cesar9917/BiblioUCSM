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
import com.cesar.biblioucsm.data.model.Book
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
    var selectedBook by remember { mutableStateOf<Book?>(null) }

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    // 🆕 Cambiado a la función equivalente en inglés
    LaunchedEffect(Unit) {
        viewModel.loadCatalog()
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
                                // 🆕 Cambiado a logout
                                viewModel.logout(context)
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
                // 🆕 Variable de búsqueda renombrada a inglés
                value = viewModel.searchQuery,
                onValueChange = { newText -> viewModel.searchQuery = newText },
                label = { Text("Buscar libro por título...") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Filtros (Se mantienen los enums, pero adaptados al nuevo nombre de variable del ViewModel)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FilterChip(selected = viewModel.selectedFilter == AvailabilityFilter.ALL, onClick = { viewModel.selectedFilter = AvailabilityFilter.ALL }, label = { Text("Todos") })
                FilterChip(selected = viewModel.selectedFilter == AvailabilityFilter.AVAILABLE, onClick = { viewModel.selectedFilter = AvailabilityFilter.AVAILABLE }, label = { Text("Disponibles") })
                FilterChip(selected = viewModel.selectedFilter == AvailabilityFilter.BORROWED, onClick = { viewModel.selectedFilter = AvailabilityFilter.BORROWED }, label = { Text("Prestados") })
            }

            // 🆕 Variable de carga renombrada a isLoading
            if (viewModel.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                // 🆕 Variable de lista renombrada a filteredBooks
                LazyColumn(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(viewModel.filteredBooks) { book ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectedBook = book } // Al hacer clic abrimos el panel
                        ) {
                            Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
                                AsyncImage(
                                    // 🆕 Propiedades del modelo Book en inglés
                                    model = book.imageUrl,
                                    contentDescription = "Portada",
                                    modifier = Modifier.size(80.dp).padding(end = 16.dp)
                                )
                                Column {
                                    Text(text = book.title, style = MaterialTheme.typography.titleMedium)
                                    Text(text = "Autor: ${book.author}", style = MaterialTheme.typography.bodyMedium)
                                }
                            }
                        }
                    }
                }
            }
        }

        // --- Panel de Detalles que se despliega al seleccionar un libro ---
        selectedBook?.let { book ->
            ModalBottomSheet(
                onDismissRequest = { selectedBook = null },
                sheetState = sheetState
            ) {
                Column(modifier = Modifier.padding(16.dp).padding(bottom = 32.dp)) {
                    AsyncImage(
                        model = book.imageUrl,
                        contentDescription = "Portada",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp),
                        contentScale = ContentScale.Fit
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = book.title, style = MaterialTheme.typography.headlineSmall)
                    Text(text = "Autor: ${book.author}", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Descripción", style = MaterialTheme.typography.titleSmall)
                    Text(text = book.descripcion ?: "Sin descripción disponible", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}