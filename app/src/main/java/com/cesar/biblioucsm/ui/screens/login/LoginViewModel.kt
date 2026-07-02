package com.cesar.biblioucsm.ui.screens.login

// Importaciones para contexto Android, estado y corrutinas
import android.content.Context
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cesar.biblioucsm.data.repository.UserRepository
import kotlinx.coroutines.launch

// ViewModel encargado de manejar la lógica del login del usuario
class LoginViewModel(private val repository: UserRepository) : ViewModel() {

    var email by mutableStateOf("")
    var password by mutableStateOf("")

    var isLoading by mutableStateOf(false)

    var errorMessage by mutableStateOf("")

    fun loginUser(
        context: Context,
        onLoginSuccess: () -> Unit
    ) {

        if (email.isBlank() || password.isBlank()) {
            errorMessage = "Por favor, llena todos los campos"
            return
        }

        viewModelScope.launch {

            isLoading = true // activa loading
            errorMessage = "" // limpia errores anteriores

            try {
                // Llamada al repositorio para validar credenciales
                val response = repository.login(email, password)

                // Si el login es exitoso y existe usuario
                if (response.success && response.user != null) {

                    // Guardar datos del usuario en SharedPreferences
                    val prefs = context.getSharedPreferences(
                        "user_prefs",
                        Context.MODE_PRIVATE
                    )

                    prefs.edit().apply {

                        // Se guardan datos basicos del usuario
                        putString("nombre", response.user.nombre)
                        putString("correo", response.user.correo)
                        apply()
                    }

                    // Navegar al catálogo después del login exitoso
                    onLoginSuccess()

                } else {
                    // Mostrar mensaje de error del backend
                    errorMessage = response.mensaje
                }

            } catch (e: Exception) {
                // Error de conexión o fallo inesperado
                errorMessage = "Error de conexión"

            } finally {
                // Se ejecuta siempre para detener loading
                isLoading = false
            }
        }
    }
}