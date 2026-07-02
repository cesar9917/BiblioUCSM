package com.cesar.biblioucsm.data.model

import com.google.gson.annotations.SerializedName

// 🆕 Modelo principal del usuario renombrado a User
data class User(
    val id: Int = 0,
    @SerializedName("nombre") val nombre: String,
    @SerializedName("correo") val correo: String,
    val contrasena: String
)

// Modelos auxiliares para las peticiones de la API
data class LoginRequest(
    val correo: String,
    val contrasena: String
)

data class AuthResponse(
    val success: Boolean,
    val mensaje: String,
    // 🆕 Propiedad actualizada para apuntar al nuevo modelo User
    @SerializedName("usuario") val user: User?
)