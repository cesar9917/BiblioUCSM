package com.cesar.biblioucsm.ui.screens.login

import android.content.Context
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cesar.biblioucsm.data.repository.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UserRepository) : ViewModel() {
    var correo by mutableStateOf("")
    var contrasena by mutableStateOf("")
    var cargando by mutableStateOf(false)
    var errorMensaje by mutableStateOf("")

    fun autenticarUsuario(context: Context, onLoginSuccess: () -> Unit) {
        if (correo.isBlank() || contrasena.isBlank()) {
            errorMensaje = "Por favor, llena todos los campos"
            return
        }

        viewModelScope.launch {
            cargando = true
            try {
                val respuesta = repository.login(correo, contrasena)

                // 1. Verificamos que sea exitoso y que el usuario NO sea nulo
                if (respuesta.success && respuesta.usuario != null) {

                    // 2. Accedemos a los datos a través de 'respuesta.usuario'
                    val prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                    prefs.edit().apply {
                        putString("nombre", respuesta.usuario.nombre)
                        putString("correo", respuesta.usuario.correo)
                        apply()
                    }
                    onLoginSuccess()
                } else {
                    errorMensaje = respuesta.mensaje
                }
            } catch (e: Exception) {
                errorMensaje = "Error de conexión"
            } finally {
                cargando = false
            }
        }
    }
}