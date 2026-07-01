package com.cesar.biblioucsm.ui.screens.catalog

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cesar.biblioucsm.data.model.Libro
import com.cesar.biblioucsm.data.repository.LibroRepository
import kotlinx.coroutines.launch

class CatalogViewModel(private val repository: LibroRepository) : ViewModel() {
    var listaLibros by mutableStateOf<List<Libro>>(emptyList())
        private set

    var cargando by mutableStateOf(false)
        private set

    var textoBusqueda by mutableStateOf("")

    val librosFiltrados: List<Libro>
        get() = if (textoBusqueda.isEmpty()) {
            listaLibros
        } else {
            listaLibros.filter { libro ->
                libro.titulo.contains(textoBusqueda, ignoreCase = true)
            }
        }
    fun cargarCatálogo() {
        viewModelScope.launch {
            cargando = true
            try {
                listaLibros = repository.getLibros()
            } catch (e: Exception) {
                // Aquí manejarías errores si la API está caída
                listaLibros = emptyList()
            } finally {
                cargando = false
            }
        }
    }
}