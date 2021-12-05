package com.sinapsis.vinilos.models.repositories
import android.app.Application
import com.sinapsis.vinilos.models.Coleccionista
import com.sinapsis.vinilos.models.servicesadapters.NetworkServiceAdapter

/**
 * Implementa el patrón Repository para las funcionalidades de Coleccionista
 */
class ColeccionistaRepository (val application: Application) {
    /**
     * Invoca el servicio del adaptador que retorna todos los coleccionistas
     */
    suspend fun getColeccionistas(): List<Coleccionista> {
        return NetworkServiceAdapter.getInstance(application).getColeccionistas()
    }

    /**
     * Invoca el servicio del adaptador que retorna un coleccionista
     */
    suspend fun getColeccionista(coleccionistaId: Int): Coleccionista {
        return NetworkServiceAdapter.getInstance(application).getColeccionista(coleccionistaId)
    }

    }





