package com.cesar.biblioucsm.data.model

data class Libro(
    val id: Int,
    val titulo: String,
    val autor: String,
    val isbn: String,
    val disponible: Boolean
)