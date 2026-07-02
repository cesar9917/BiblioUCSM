package com.cesar.biblioucsm.ui.screens.detail

// Importaciones de Compose y ViewModel para manejo de estado y lógica
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cesar.biblioucsm.data.model.Book
import com.cesar.biblioucsm.data.repository.BookRepository
import kotlinx.coroutines.launch

// ViewModel encargado de manejar el detalle de un libro
class DetailViewModel(private val repository: BookRepository) : ViewModel() {

    // Estado del libro seleccionado (puede ser null inicialmente)
    var book by mutableStateOf<Book?>(null)

    // Estado de carga para controlar el loading en la UI
    var isLoading by mutableStateOf(true)

    // Función que obtiene el detalle de un libro por su ID
    fun loadBookDetail(bookId: Int) {

        // Ejecuta una corrutina en el scope del ViewModel
        viewModelScope.launch {

            isLoading = true // activa estado de carga

            try {
                // Llama al repositorio para obtener el libro por ID
                book = repository.getBookById(bookId)

            } catch (e: Exception) {
                // Manejo de errores (se puede mejorar con logs o UI error)
            } finally {
                // Se ejecuta siempre, haya error o no
                isLoading = false
            }
        }
    }
}