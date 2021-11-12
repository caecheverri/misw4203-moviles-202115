package com.sinapsis.vinilos.models

/**
 * Implementa el modelo de datos para un Artista
 */

data class Artista (
    val artistaId: Int,
    val nombre: String,
    val imagen: String,
    val descripcion: String = "",
    val fechaNacimiento: String = ""
    )
