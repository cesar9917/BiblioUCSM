package com.cesar.biblioucsm.ui.screens.account

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen(navController: NavController, viewModel: AccountViewModel = viewModel()) {
    val context = LocalContext.current

    // 🆕 Cambiado a la función equivalente en inglés que estructuraremos en tu ViewModel
    LaunchedEffect(Unit) { viewModel.loadUserData(context) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mi Cuenta") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                Icons.Default.Person,
                contentDescription = null,
                modifier = Modifier.size(120.dp),
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(text = "Nombre:", style = MaterialTheme.typography.labelLarge)
                    // 🆕 Variables del ViewModel actualizadas a inglés
                    Text(text = viewModel.userName, style = MaterialTheme.typography.headlineSmall)

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(text = "Correo:", style = MaterialTheme.typography.labelLarge)
                    Text(text = viewModel.userEmail, style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}