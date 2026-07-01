package com.cesar.biblioucsm.ui.screens.detail

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.cesar.biblioucsm.data.model.Libro
import com.cesar.biblioucsm.data.repository.LibroRepository
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: LibroRepository) : ViewModel() {
    var libro by mutableStateOf<Libro?>(null)
    var cargando by mutableStateOf(true)

    fun obtenerDetalle(libroId: Int) {
        viewModelScope.launch {
            cargando = true
            try {
                libro = repository.getLibroById(libroId)
            } catch (e: Exception) {
                // Manejo de error
            } finally {
                cargando = false
            }
        }
    }
}