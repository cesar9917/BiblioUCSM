package com.cesar.biblioucsm.data.repository

import com.cesar.biblioucsm.data.model.*
import com.cesar.biblioucsm.data.network.ApiService

class UserRepository(private val apiService: ApiService) {

    suspend fun register(user: User): AuthResponse {
        return apiService.registerUser(user)
    }

    suspend fun login(correo: String, contrasena: String): AuthResponse {
        return apiService.loginUser(LoginRequest(correo, contrasena))
    }
}