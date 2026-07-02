package com.cesar.biblioucsm.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cesar.biblioucsm.data.repository.BookRepository
import com.cesar.biblioucsm.ui.screens.detail.DetailViewModel

// Factory para crear el ViewModel DetailViewModel con dependencias (Repository)
class DetailViewModelFactory(private val repository: BookRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetailViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}