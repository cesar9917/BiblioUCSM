package com.cesar.biblioucsm.ui.screens.account

import android.content.Context
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel

// ViewModel encargado de manejar los datos de la pantalla Account
class AccountViewModel : ViewModel() {
    var userName by mutableStateOf("")
    var userEmail by mutableStateOf("")

    // Carga los datos del usuario desde SharedPreferences
    fun loadUserData(context: Context) {
        val prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        userName = prefs.getString("nombre", "User") ?: "User"
        userEmail = prefs.getString("correo", "Not available") ?: "Not available"
    }
}