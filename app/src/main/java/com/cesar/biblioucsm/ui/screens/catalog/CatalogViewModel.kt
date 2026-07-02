package com.cesar.biblioucsm.ui.screens.catalog

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cesar.biblioucsm.data.model.Book
import com.cesar.biblioucsm.data.repository.BookRepository
import kotlinx.coroutines.launch
import android.content.Context

// 🆕 Cambiado a inglés para mantener la consistencia
enum class AvailabilityFilter {
    ALL, AVAILABLE, BORROWED
}

class CatalogViewModel(private val repository: BookRepository) : ViewModel() {

    // 🆕 Estados traducidos y listos para la interfaz
    var booksList by mutableStateOf<List<Book>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var searchQuery by mutableStateOf("")

    var selectedFilter by mutableStateOf(AvailabilityFilter.ALL)

    // 🆕 Getter dinámico con filtros actualizados a las propiedades en inglés de Book
    val filteredBooks: List<Book>
        get() {
            val booksByText = if (searchQuery.isEmpty()) {
                booksList
            } else {
                booksList.filter { it.title.contains(searchQuery, ignoreCase = true) }
            }

            return when (selectedFilter) {
                AvailabilityFilter.ALL -> booksByText
                AvailabilityFilter.AVAILABLE -> booksByText.filter { it.disponible == 1 }
                AvailabilityFilter.BORROWED -> booksByText.filter { it.disponible == 0 }
            }
        }

    // 🆕 Función de carga adaptada a BookRepository
    fun loadCatalog() {
        viewModelScope.launch {
            isLoading = true
            try {
                booksList = repository.getBooks()
            } catch (e: Exception) {
                // Manejo de errores
            } finally {
                isLoading = false
            }
        }
    }

    // 🆕 Nombre estandarizado para limpiar preferencias
    fun logout(context: Context) {
        val prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        prefs.edit().clear().apply()
    }
}