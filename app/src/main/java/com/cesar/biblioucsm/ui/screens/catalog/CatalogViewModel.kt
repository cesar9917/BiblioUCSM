package com.cesar.biblioucsm.ui.screens.catalog

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cesar.biblioucsm.data.model.Libro
import com.cesar.biblioucsm.data.repository.LibroRepository
import kotlinx.coroutines.launch
import android.content.Context

enum class FiltroDisponibilidad {
    TODOS, DISPONIBLES, PRESTADOS
}

class CatalogViewModel(private val repository: LibroRepository) : ViewModel() {
    var listaLibros by mutableStateOf<List<Libro>>(emptyList())
        private set

    var cargando by mutableStateOf(false)
        private set

    var textoBusqueda by mutableStateOf("")

    var filtroSeleccionado by mutableStateOf(FiltroDisponibilidad.TODOS)

    val librosFiltrados: List<Libro>
        get() {
            // Primero filtramos por texto
            val librosPorTexto = if (textoBusqueda.isEmpty()) {
                listaLibros
            } else {
                listaLibros.filter { it.titulo.contains(textoBusqueda, ignoreCase = true) }
            }

            // Luego, sobre ese resultado, aplicamos el filtro de disponibilidad
            return when (filtroSeleccionado) {
                FiltroDisponibilidad.TODOS -> librosPorTexto
                FiltroDisponibilidad.DISPONIBLES -> librosPorTexto.filter { it.disponible == 1 }
                FiltroDisponibilidad.PRESTADOS -> librosPorTexto.filter { it.disponible == 0 }
            }
        }

    fun cargarCatalogo() {
        viewModelScope.launch {
            cargando = true
            try {
                listaLibros = repository.getLibros()
            } catch (e: Exception) {
                // Manejo de errores
            } finally {
                cargando = false
            }
        }
    }

    // Importa Context si necesitas borrar SharedPreferences
    fun cerrarSesion(context: Context) {
        val prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        prefs.edit().clear().apply()
        // Si tienes algún estado de usuario en el ViewModel, límpialo aquí también
    }
}