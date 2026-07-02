package com.cesar.biblioucsm.data.repository

import com.cesar.biblioucsm.data.model.Book
import com.cesar.biblioucsm.data.network.ApiService

class BookRepository(private val apiService: ApiService) {

    suspend fun getBooks(): List<Book> {
        return apiService.getBooks()
    }

    suspend fun getBookById(id: Int): Book {
        return apiService.getBookById(id)
    }
}