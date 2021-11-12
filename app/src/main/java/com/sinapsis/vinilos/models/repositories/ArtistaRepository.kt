package com.sinapsis.vinilos.models.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.sinapsis.vinilos.models.Artista
import com.sinapsis.vinilos.models.servicesadapters.NetworkServiceAdapter

/**
 * Implementa el patrón Repository para las funcionalidades de artistas
 */
class ArtistaRepository (val application: Application) {
    /**
     * Invoca el servicio del adaptador que retorna todos los artistas
     */
    fun getArtistas(callback: (List<Artista>)->Unit, onError: (VolleyError)->Unit) {
        NetworkServiceAdapter.getInstance(application).getArtistas({
            callback(it)},
            onError
        )
    }

    /**
     * Invoca el servicio del adaptador que retorna un artista
     */
    fun getArtista(artistaId: Int, callback: (Artista)->Unit, onError: (VolleyError)->Unit) {
        NetworkServiceAdapter.getInstance(application).getArtista(artistaId, {
            callback(it)},
            onError
        )
    }
}