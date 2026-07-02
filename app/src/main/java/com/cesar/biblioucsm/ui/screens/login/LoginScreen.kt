package com.cesar.biblioucsm.ui.screens.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext

// Pantalla de inicio de sesión de la aplicación
@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onNavigateToCatalog: () -> Unit // navegación al catálogo después del login
) {

    // Contexto de Android necesario para autenticación o SharedPreferences
    val context = LocalContext.current

    // Contenedor principal centrado en pantalla
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Título de la aplicación
        Text(
            text = "BiblioUCSM",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Campo de correo institucional
        OutlinedTextField(
            value = viewModel.email,
            onValueChange = { viewModel.email = it },
            label = { Text("Correo Institucional") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de contraseña con ocultamiento de texto
        OutlinedTextField(
            value = viewModel.password,
            onValueChange = { viewModel.password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        // Mostrar mensaje de error si existe
        if (viewModel.errorMessage.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = viewModel.errorMessage,
                color = MaterialTheme.colorScheme.error
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Mostrar indicador de carga o botón de login
        if (viewModel.isLoading) {

            // Indicador de carga mientras se valida usuario
            CircularProgressIndicator()

        } else {

            // BotOn para iniciar sesión
            Button(
                onClick = {
                    viewModel.loginUser(
                        context,
                        onLoginSuccess = onNavigateToCatalog
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Iniciar Sesión")
            }
        }
    }
}