package com.sinapsis.vinilos.models

/**
 * Implementa el modelo de datos para un Coleccionista
 */

data class Coleccionista (
    val coleccionistaId: Int,
    val nombreColeccionista: String,
    val telefonoColeccionista: String,
    val emailColeccionista: String,
    val commentsColeccionista: String,
    val favoritePerformersColeccionista: String
)