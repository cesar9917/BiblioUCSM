package com.cesar.biblioucsm.data.network

import com.cesar.biblioucsm.data.model.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("libros")
    suspend fun obtenerLibros(): List<Libro>

    // 🆕 Rutas para la autenticación
    @POST("usuarios/registrar")
    suspend fun registrarUsuario(@Body usuario: Usuario): AuthResponse

    @POST("usuarios/login")
    suspend fun iniciarSesion(@Body request: LoginRequest): AuthResponse

    @GET("libros/{id}") // Ajusta esta ruta según la estructura de tu API
    suspend fun obtenerLibroPorId(@Path("id") id: Int): Libro

    @GET("api/libros/{id}")
    suspend fun getLibroById(@Path("id") id: Int): Libro
}