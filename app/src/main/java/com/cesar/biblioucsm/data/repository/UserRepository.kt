package com.cesar.biblioucsm.data.repository

import com.cesar.biblioucsm.data.model.*
import com.cesar.biblioucsm.data.network.ApiService

// Repositorio encargado de gestionar las operaciones de usuario (register y login) con la API
class UserRepository(private val apiService: ApiService) {

    suspend fun register(user: User): AuthResponse {
        return apiService.registerUser(user)
    }

    suspend fun login(correo: String, contrasena: String): AuthResponse {
        return apiService.loginUser(LoginRequest(correo, contrasena))
    }
}