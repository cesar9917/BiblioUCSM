package com.cesar.biblioucsm.data.repository

import com.cesar.biblioucsm.data.model.Book
import com.cesar.biblioucsm.data.network.ApiService

// Repositorio encargado de gestionar el acceso a datos de libros desde la API
class BookRepository(private val apiService: ApiService) {

    suspend fun getBooks(): List<Book> {
        return apiService.getBooks()
    }

    suspend fun getBookById(id: Int): Book {
        return apiService.getBookById(id)
    }
}