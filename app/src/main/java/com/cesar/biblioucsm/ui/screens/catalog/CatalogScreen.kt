
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

    var expanded by remember {
        mutableStateOf(false)
    }

    var selectedBook by remember {
        mutableStateOf<Book?>(null)
    }

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    // Cargar libros al entrar al catálogo
    LaunchedEffect(Unit) {
        viewModel.loadCatalog()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Biblioteca UCSM - Catálogo")
                },
                actions = {
                    IconButton(
                        onClick = {
                            expanded = true
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.AccountCircle,
                            contentDescription = "Perfil"
                        )
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = {
                            expanded = false
                        }
                    ) {
                        DropdownMenuItem(
                            text = {
                                Text("Ver Cuenta")
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Filled.Person,
                                    contentDescription = null
                                )
                            },
                            onClick = {
                                expanded = false
                                navController.navigate(
                                    Screen.Account.route
                                )
                            }
                        )

                        DropdownMenuItem(
                            text = {
                                Text("Cerrar Sesión")
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Filled.ExitToApp,
                                    contentDescription = null
                                )
                            },
                            onClick = {
                                expanded = false

                                viewModel.logout(context)

                                navController.navigate(
                                    Screen.Login.route
                                ) {
                                    popUpTo(
                                        Screen.Catalog.route
                                    ) {
                                        inclusive = true
                                    }
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

            // Buscador
            OutlinedTextField(
                value = viewModel.searchQuery,
                onValueChange = { newText ->
                    viewModel.searchQuery = newText
                },
                label = {
                    Text("Buscar libro por título...")
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            // Filtros de disponibilidad
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                FilterChip(
                    selected = viewModel.selectedFilter ==
                            AvailabilityFilter.ALL,
                    onClick = {
                        viewModel.selectedFilter =
                            AvailabilityFilter.ALL
                    },
                    label = {
                        Text("Todos")
                    }
                )

                FilterChip(
                    selected = viewModel.selectedFilter ==
                            AvailabilityFilter.AVAILABLE,
                    onClick = {
                        viewModel.selectedFilter =
                            AvailabilityFilter.AVAILABLE
                    },
                    label = {
                        Text("Disponibles")
                    }
                )

                FilterChip(
                    selected = viewModel.selectedFilter ==
                            AvailabilityFilter.BORROWED,
                    onClick = {
                        viewModel.selectedFilter =
                            AvailabilityFilter.BORROWED
                    },
                    label = {
                        Text("Prestados")
                    }
                )
            }

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            if (viewModel.isLoading) {

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }

            } else {

                val books = viewModel.filteredBooks

                if (books.isEmpty()) {

                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No hay libros para mostrar",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }

                } else {

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement =
                            Arrangement.spacedBy(8.dp)
                    ) {

                        items(books) { book ->

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        selectedBook = book
                                    }
                            ) {

                                Row(
                                    modifier = Modifier.padding(8.dp),
                                    verticalAlignment =
                                        Alignment.CenterVertically
                                ) {

                                    AsyncImage(
                                        model = book.imageUrl,
                                        contentDescription =
                                            "Portada del libro",
                                        modifier = Modifier
                                            .size(80.dp)
                                            .padding(end = 16.dp),
                                        contentScale =
                                            ContentScale.Fit
                                    )

                                    Column {

                                        // Evitar cierre por título nulo
                                        Text(
                                            text = book.title
                                                ?: "Sin título",
                                            style = MaterialTheme
                                                .typography.titleMedium
                                        )

                                        Spacer(
                                            modifier =
                                                Modifier.height(4.dp)
                                        )

                                        Text(
                                            text = "Autor: ${
                                                book.author
                                                    ?: "Desconocido"
                                            }",
                                            style = MaterialTheme
                                                .typography.bodyMedium
                                        )

                                        Spacer(
                                            modifier =
                                                Modifier.height(4.dp)
                                        )

                                        Text(
                                            text = if (
                                                book.disponible == 1
                                            ) {
                                                "Disponible"
                                            } else {
                                                "Prestado"
                                            },
                                            style = MaterialTheme
                                                .typography.bodySmall
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Panel inferior con detalles del libro
    selectedBook?.let { book ->

        ModalBottomSheet(
            onDismissRequest = {
                selectedBook = null
            },
            sheetState = sheetState
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .padding(bottom = 32.dp)
            ) {

                AsyncImage(
                    model = book.imageUrl,
                    contentDescription = "Portada del libro",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp),
                    contentScale = ContentScale.Fit
                )

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                Text(
                    text = book.title ?: "Sin título",
                    style = MaterialTheme.typography.headlineSmall
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = "Autor: ${
                        book.author ?: "Desconocido"
                    }",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = "ISBN: ${
                        book.isbn ?: "No disponible"
                    }",
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = if (book.disponible == 1) {
                        "Estado: Disponible"
                    } else {
                        "Estado: Prestado"
                    },
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                Text(
                    text = "Descripción",
                    style = MaterialTheme.typography.titleSmall
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = book.descripcion
                        ?.takeIf { it.isNotBlank() }
                        ?: "Sin descripción disponible",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
