package com.cesar.biblioucsm.ui.screens.login

import android.content.Context
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cesar.biblioucsm.data.repository.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UserRepository) : ViewModel() {

    // 🆕 Variables de estado traducidas al inglés para la UI
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf("")

    // 🆕 Método de autenticación estandarizado
    fun loginUser(context: Context, onLoginSuccess: () -> Unit) {
        if (email.isBlank() || password.isBlank()) {
            errorMessage = "Por favor, llena todos los campos"
            return
        }

        viewModelScope.launch {
            isLoading = true
            errorMessage = "" // Limpiamos errores previos al intentar conectar
            try {
                // Llamada a tu repositorio con el método limpio 'login'
                val response = repository.login(email, password)

                // Verificamos el éxito y accedemos al objeto User de la respuesta
                if (response.success && response.user != null) {
                    val prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                    prefs.edit().apply {
                        // Mantenemos las llaves locales en español para no romper la carga en AccountViewModel
                        putString("nombre", response.user.nombre)
                        putString("correo", response.user.correo)
                        apply()
                    }
                    onLoginSuccess()
                } else {
                    errorMessage = response.mensaje
                }
            } catch (e: Exception) {
                errorMessage = "Error de conexión"
            } finally {
                isLoading = false
            }
        }
    }
}