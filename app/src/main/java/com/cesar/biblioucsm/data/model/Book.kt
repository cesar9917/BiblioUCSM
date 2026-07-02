package com.cesar.biblioucsm.data.model

import com.google.gson.annotations.SerializedName

// Modelo que representa un libro recibido desde la API
data class Book(
    val id: Int,
    val isbn: String,
    @SerializedName("titulo") val title: String,
    @SerializedName("autor") val author: String,
    @SerializedName("imagenUrl") val imageUrl: String,
    val disponible: Int,
    @SerializedName("descripcion") val descripcion: String? = null
)