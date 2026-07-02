package com.cesar.biblioucsm.ui.screens.detail

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cesar.biblioucsm.data.model.Book
import com.cesar.biblioucsm.data.repository.BookRepository
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: BookRepository) : ViewModel() {
    var book by mutableStateOf<Book?>(null)

    // 🆕 Estado de carga renombrado a inglés
    var isLoading by mutableStateOf(true)

    // 🆕 Función estandarizada a la nomenclatura del proyecto
    fun loadBookDetail(bookId: Int) {
        viewModelScope.launch {
            isLoading = true
            try {
                // 🆕 Llamada al repositorio con el método actualizado
                book = repository.getBookById(bookId)
            } catch (e: Exception) {
                // Manejo de error
            } finally {
                isLoading = false
            }
        }
    }
}