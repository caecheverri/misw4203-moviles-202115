package com.sinapsis.vinilos.models.repositories
import android.app.Application
import com.sinapsis.vinilos.models.Coleccionista
import com.sinapsis.vinilos.models.servicesadapters.NetworkServiceAdapter


/**
 * Implementa el patrón Repository para las funcionalidades de artistas
 */
class ColeccionistaRepository (val application: Application) {
    /**
     * Invoca el servicio del adaptador que retorna todos los artistas
     */
    suspend fun getColeccionistas(): List<Coleccionista> {
        return NetworkServiceAdapter.getInstance(application).getColeccionistas()
    }

    /**
     * Invoca el servicio del adaptador que retorna un artista
     */
    suspend fun getColeccionista(coleccionistaId: Int): Coleccionista {
        return NetworkServiceAdapter.getInstance(application).getColeccionista(coleccionistaId)
    }

}