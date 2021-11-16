package com.sinapsis.vinilos.models.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.sinapsis.vinilos.models.Coleccionista
import com.sinapsis.vinilos.models.servicesadapters.NetworkServiceAdapter

/**
 * Implementa el patrón Repository para las funcionalidades de Coleccionistas
 */
class ColeccionistaRepository (val application: Application) {
    /**
     * Invoca el servicio del adaptador que retorna todos los artistas
     */
    fun getColeccionistas(callback: (List<Coleccionista>)->Unit, onError: (VolleyError)->Unit) {
        NetworkServiceAdapter.getInstance(application).getColeccionistas({
            callback(it)},
            onError
        )
    }
}