
package com.cesar.biblioucsm.data.model

import com.google.gson.annotations.SerializedName

// Modelo de libro compatible con la API Node.js
data class Book(
    @SerializedName("id")
    val id: Int,

    @SerializedName("isbn")
    val isbn: String? = null,

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("author")
    val author: String? = null,

    @SerializedName("imageUrl")
    val imageUrl: String? = null,

    @SerializedName("disponible")
    val disponible: Int = 1,

    @SerializedName("descripcion")
    val descripcion: String? = null
)
