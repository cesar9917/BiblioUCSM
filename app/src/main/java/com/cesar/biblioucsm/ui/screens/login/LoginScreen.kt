package com.cesar.biblioucsm.ui.screens.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onNavigateToCatalog: () -> Unit // 🆕 Cambiado para coincidir perfectamente con tu NavGraph
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "BiblioUCSM", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            // 🆕 Estados del ViewModel actualizados a inglés (email)
            value = viewModel.email,
            onValueChange = { viewModel.email = it },
            label = { Text("Correo Institucional") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            // 🆕 Estados del ViewModel actualizados a inglés (password)
            value = viewModel.password,
            onValueChange = { viewModel.password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        // 🆕 Variable de error renombrada a errorMessage
        if (viewModel.errorMessage.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = viewModel.errorMessage, color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 🆕 Estado de carga renombrado a isLoading
        if (viewModel.isLoading) {
            CircularProgressIndicator()
        } else {
            Button(
                // 🆕 Método de autenticación actualizado a loginUser
                onClick = { viewModel.loginUser(context, onLoginSuccess = onNavigateToCatalog) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Iniciar Sesión")
            }
        }
    }
}