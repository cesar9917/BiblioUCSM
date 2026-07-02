package com.cesar.biblioucsm.ui.screens.account

import android.content.Context
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel

class AccountViewModel : ViewModel() {

    // 🆕 Estados traducidos para la UI de la cuenta
    var userName by mutableStateOf("")
    var userEmail by mutableStateOf("")

    // 🆕 Función de carga adaptada al estándar del proyecto
    fun loadUserData(context: Context) {
        val prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

        // Mantenemos "nombre" y "correo" si son las llaves con las que guardas en el Login
        userName = prefs.getString("nombre", "User") ?: "User"
        userEmail = prefs.getString("correo", "Not available") ?: "Not available"
    }
}