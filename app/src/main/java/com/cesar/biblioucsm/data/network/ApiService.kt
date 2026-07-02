package com.cesar.biblioucsm.data.network

import com.cesar.biblioucsm.data.model.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @GET("libros")
    suspend fun getBooks(): List<Book>

    @GET("libros/{id}")
    suspend fun getBookById(@Path("id") id: Int): Book

    @POST("usuarios/registrar")
    suspend fun registerUser(@Body user: User): AuthResponse

    @POST("usuarios/login")
    suspend fun loginUser(@Body request: LoginRequest): AuthResponse
}