package com.cesar.biblioucsm.data.model

// Modelo principal del usuario
data class Usuario(
    val id: Int = 0,
    val nombre: String,
    val correo: String,
    val contrasena: String // En producción esto se encripta, pero para el curso va como String
)

// Modelos auxiliares para las peticiones de la API
data class LoginRequest(
    val correo: String,
    val contrasena: String
)

data class AuthResponse(
    val success: Boolean,
    val mensaje: String,
    val usuario: Usuario?
)