package com.cesar.biblioucsm.ui.screens.catalog

// Importaciones necesarias para estados, ViewModel y corrutinas
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cesar.biblioucsm.data.model.Book
import com.cesar.biblioucsm.data.repository.BookRepository
import kotlinx.coroutines.launch
import android.content.Context

// Enum que define los estados de filtrado del catálogo
enum class AvailabilityFilter {
    ALL,
    AVAILABLE,
    BORROWED
}

// ViewModel que maneja la lógica del catálogo de libros
class CatalogViewModel(private val repository: BookRepository) : ViewModel() {

    // Lista principal de libros obtenidos del repositorio
    var booksList by mutableStateOf<List<Book>>(emptyList())
        private set // Solo el ViewModel puede modificarla

    // Estado de carga para mostrar o ocultar loading
    var isLoading by mutableStateOf(false)
        private set

    // Texto de búsqueda ingresado por el usuario
    var searchQuery by mutableStateOf("")

    // Filtro seleccionado actualmente (todos, disponibles o prestados)
    var selectedFilter by mutableStateOf(AvailabilityFilter.ALL)

    // Lista calculada dinámicamente según búsqueda y filtro
    val filteredBooks: List<Book>
        get() {

            // Filtrado por texto (título del libro)
            val booksByText = if (searchQuery.isEmpty()) {
                booksList // si no hay búsqueda, usa toda la lista
            } else {
                // filtra libros que contengan el texto buscado
                booksList.filter {
                    it.title.contains(searchQuery, ignoreCase = true)
                }
            }

            // Aplicación del filtro por disponibilidad
            return when (selectedFilter) {

                AvailabilityFilter.ALL ->
                    booksByText // muestra todos

                AvailabilityFilter.AVAILABLE ->
                    booksByText.filter { it.disponible == 1 } // solo disponibles

                AvailabilityFilter.BORROWED ->
                    booksByText.filter { it.disponible == 0 } // solo prestados
            }
        }

    // Función para cargar el catálogo desde el repositorio
    fun loadCatalog() {

        // Ejecuta corrutina en el scope del ViewModel
        viewModelScope.launch {

            isLoading = true // activa loading

            try {
                // Obtiene libros desde el repositorio
                booksList = repository.getBooks()

            } catch (e: Exception) {
                // Manejo de errores (puede mejorarse con logs o UI error)
            } finally {
                isLoading = false // desactiva loading
            }
        }
    }

    // FunciOn para cerrar sesiOn del usuario
    fun logout(context: Context) {

        // Accede a SharedPreferences del usuario
        val prefs = context.getSharedPreferences(
            "user_prefs",
            Context.MODE_PRIVATE
        )

        // Limpia toda la sesiOn guardada
        prefs.edit().clear().apply()
    }
}