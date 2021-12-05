package com.sinapsis.vinilos.models

/**
 * Implementa el modelo de datos para el detalle artistas favoritos de coleccionista
 */

data class ColeccionistaFav(
    val favId: Int,
    val nombreFav: String,
    val imagenFav: String,
    val descripcionFav: String
)