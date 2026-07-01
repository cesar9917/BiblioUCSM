package com.cesar.biblioucsm.data.repository

import com.cesar.biblioucsm.data.model.*
import com.cesar.biblioucsm.data.network.ApiService

class UserRepository(private val apiService: ApiService) {
    suspend fun registrar(usuario: Usuario): AuthResponse {
        return apiService.registrarUsuario(usuario)
    }

    suspend fun login(correo: String, contrasena: String): AuthResponse {
        return apiService.iniciarSesion(LoginRequest(correo, contrasena))
    }
}