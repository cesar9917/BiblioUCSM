package com.cesar.biblioucsm.ui.screens.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cesar.biblioucsm.data.repository.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UserRepository) : ViewModel() {

    var correo by mutableStateOf("")
    var contrasena by mutableStateOf("")
    var cargando by mutableStateOf(false)
    var errorMensaje by mutableStateOf("")

    fun autenticarUsuario(onLoginSuccess: () -> Unit) {
        if (correo.isBlank() || contrasena.isBlank()) {
            errorMensaje = "Por favor, llena todos los campos"
            return
        }

        viewModelScope.launch {
            cargando = true
            errorMensaje = ""
            try {
                val respuesta = repository.login(correo, contrasena)
                if (respuesta.success) {
                    onLoginSuccess() // Callback para cambiar de pantalla
                } else {
                    errorMensaje = respuesta.mensaje
                }
            } catch (e: Exception) {
                errorMensaje = "Error de conexión con el servidor"
            } finally {
                cargando = false
            }
        }
    }
}