package com.cesar.biblioucsm.data.network

import com.cesar.biblioucsm.data.model.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("libros")
    suspend fun obtenerLibros(): List<Libro>

    // 🆕 Rutas para la autenticación
    @POST("usuarios/registrar")
    suspend fun registrarUsuario(@Body usuario: Usuario): AuthResponse

    @POST("usuarios/login")
    suspend fun iniciarSesion(@Body request: LoginRequest): AuthResponse
}