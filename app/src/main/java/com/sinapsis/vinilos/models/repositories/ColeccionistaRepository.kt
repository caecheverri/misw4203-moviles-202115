package com.sinapsis.vinilos.models.repositories

import android.app.Application
import com.sinapsis.vinilos.models.Coleccionista
import com.sinapsis.vinilos.models.servicesadapters.NetworkServiceAdapter

/**
 * Implementa el patrón Repository para las funcionalidades de Coleccionistas
 */
class ColeccionistaRepository (val application: Application) {
    /**
     * Invoca el servicio del adaptador que retorna todos los artistas
     */
    suspend fun getColeccionistas(): List<Coleccionista>{
        //Determinar la fuente de datos que se va a utilizar. Si es necesario consultar la red, ejecutar el siguiente código
        return NetworkServiceAdapter.getInstance(application).getColeccionistas()
    }
}