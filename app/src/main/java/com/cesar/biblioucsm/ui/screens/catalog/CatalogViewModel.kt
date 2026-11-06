
package com.cesar.biblioucsm.ui.screens.catalog

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cesar.biblioucsm.data.model.Book
import com.cesar.biblioucsm.data.repository.BookRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch

// Estados de disponibilidad
enum class AvailabilityFilter {
    ALL,
    AVAILABLE,
    BORROWED
}

// ViewModel del catálogo
class CatalogViewModel(
    private val repository: BookRepository
) : ViewModel() {

    // Lista de libros
    var booksList by mutableStateOf<List<Book>>(emptyList())
        private set

    // Estado de carga
    var isLoading by mutableStateOf(false)
        private set

    // Mensaje de error
    var errorMessage by mutableStateOf<String?>(null)
        private set

    // Buscador
    var searchQuery by mutableStateOf("")

    // Filtro seleccionado
    var selectedFilter by mutableStateOf(
        AvailabilityFilter.ALL
    )

    // Libros filtrados
    val filteredBooks: List<Book>
        get() {
            val query = searchQuery.trim()

            val booksByText = if (query.isEmpty()) {
                booksList
            } else {
                booksList.filter { book ->
                    book.title.orEmpty().contains(
                        query,
                        ignoreCase = true
                    )
                }
            }

            return when (selectedFilter) {

                AvailabilityFilter.ALL -> booksByText

                AvailabilityFilter.AVAILABLE ->
                    booksByText.filter {
                        it.disponible == 1
                    }

                AvailabilityFilter.BORROWED ->
                    booksByText.filter {
                        it.disponible == 0
                    }
            }
        }

    // Obtener libros desde la API
    fun loadCatalog() {
        viewModelScope.launch {

            isLoading = true
            errorMessage = null

            try {
                booksList = repository.getBooks()

                Log.d(
                    "BiblioUCSM",
                    "Libros cargados: ${booksList.size}"
                )

            } catch (e: CancellationException) {
                throw e

            } catch (e: Exception) {

                errorMessage = e.message
                    ?: "No se pudo cargar el catálogo"

                Log.e(
                    "BiblioUCSM",
                    "Error al cargar libros",
                    e
                )

            } finally {
                isLoading = false
            }
        }
    }

    // Cerrar sesión
    fun logout(context: Context) {
        val prefs = context.getSharedPreferences(
            "user_prefs",
            Context.MODE_PRIVATE
        )

        prefs.edit().clear().apply()
    }
}
