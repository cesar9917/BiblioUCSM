package com.cesar.biblioucsm.ui.screens.catalog

// Importaciones de UI y navegacion en Jetpack Compose
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

// Pantalla principal del catalogo de libros
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

    // Estado del Bottom Sheet
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    // Carga inicial del catalogo cuando entra a la pantalla
    LaunchedEffect(Unit) {
        viewModel.loadCatalog()
    }

    // Estructura principal de la pantalla
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Biblioteca UCSM - Catálogo") },
                // Acciones del TopBar
                actions = {
                    // Booón de perfil
                    IconButton(onClick = { expanded = true }) {
                        Icon(Icons.Filled.AccountCircle, contentDescription = "Perfil")
                    }
                    // Opcion: ver cuenta
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
                        // Opcion: cerrar sesión
                        DropdownMenuItem(
                            text = { Text("Cerrar Sesión") },
                            leadingIcon = { Icon(Icons.Filled.ExitToApp, contentDescription = null) },
                            onClick = {
                                expanded = false
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
        // Contenedor principal de la pantalla
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Campo de busqueda de libros
            OutlinedTextField(
                value = viewModel.searchQuery,
                onValueChange = { newText -> viewModel.searchQuery = newText },
                label = { Text("Buscar libro por título...") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Filtros por estado del libro
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                // Filtro: todos los libros
                FilterChip(selected = viewModel.selectedFilter == AvailabilityFilter.ALL, onClick = { viewModel.selectedFilter = AvailabilityFilter.ALL }, label = { Text("Todos") })
                // Filtro: libros disponibles
                FilterChip(selected = viewModel.selectedFilter == AvailabilityFilter.AVAILABLE, onClick = { viewModel.selectedFilter = AvailabilityFilter.AVAILABLE }, label = { Text("Disponibles") })
                // Filtro: libros prestados
                FilterChip(selected = viewModel.selectedFilter == AvailabilityFilter.BORROWED, onClick = { viewModel.selectedFilter = AvailabilityFilter.BORROWED }, label = { Text("Prestados") })
            }

            // Mostrar loader si está cargando datos
            if (viewModel.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                // Lista de libros filtrados
                LazyColumn(
                    modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    // Renderiza cada libro en la lista
                    items(viewModel.filteredBooks
                    ) { book ->
                        // Tarjeta de cada libro
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectedBook = book } // Al hacer clic abrimos el panel
                        ) {
                            // Contenido del libro
                            Row(
                                modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Imagen del libro
                                AsyncImage(
                                    model = book.imageUrl,
                                    contentDescription = "Portada",
                                    modifier = Modifier.size(80.dp).padding(end = 16.dp)
                                )
                                // Informacion del libro
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

        /// Panel inferior de detalles del libro seleccionado
        selectedBook?.let { book ->
            ModalBottomSheet(
                onDismissRequest = { selectedBook = null },
                sheetState = sheetState
            ) {
                // Contenido del detalle del libro
                Column(
                    modifier = Modifier.padding(16.dp).padding(bottom = 32.dp)
                ) {
                    // Imagen grande del libro
                    AsyncImage(
                        model = book.imageUrl,
                        contentDescription = "Portada",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp),
                        contentScale = ContentScale.Fit
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    // Título del libro
                    Text(
                        text = book.title, style = MaterialTheme.typography.headlineSmall
                    )
                    // Autor del libro
                    Text(
                        text = "Autor: ${book.author}", style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    // Descripción del libro
                    Text(
                        text = "Descripción", style = MaterialTheme.typography.titleSmall
                    )
                    // Texto de descripción
                    Text(
                        text = book.descripcion ?: "Sin descripción disponible", style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}