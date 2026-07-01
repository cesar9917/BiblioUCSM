package com.cesar.biblioucsm.data.repository

import com.cesar.biblioucsm.data.model.Libro
import com.cesar.biblioucsm.data.network.ApiService

class LibroRepository(private val apiService: ApiService) {
    suspend fun getLibros(): List<Libro> {
        return apiService.obtenerLibros()
    }

    suspend fun getLibroById(id: Int): Libro {
        return apiService.obtenerLibroPorId(id)
    }
}