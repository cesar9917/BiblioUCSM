package com.cesar.biblioucsm.ui.screens.account

import android.content.Context
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel

class AccountViewModel : ViewModel() {
    var nombreUsuario by mutableStateOf("")
    var correoUsuario by mutableStateOf("")
    fun cargarDatos(context: Context) {
        val prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        nombreUsuario = prefs.getString("nombre", "Usuario") ?: "Usuario"
        correoUsuario = prefs.getString("correo", "No disponible") ?: "No disponible"
    }
}