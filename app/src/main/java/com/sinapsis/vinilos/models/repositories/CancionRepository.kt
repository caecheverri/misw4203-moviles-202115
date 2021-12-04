package com.sinapsis.vinilos.models.repositories

import android.app.Application
import com.sinapsis.vinilos.models.Cancion
import com.sinapsis.vinilos.models.servicesadapters.NetworkServiceAdapter

class CancionRepository (val application: Application){
    suspend fun refreshData(albumId: Int): List<Cancion> {
        return NetworkServiceAdapter.getInstance(application).getListCancion(albumId)
    }

    suspend fun postCancion(newCancion: Cancion, albumId: Int,): Cancion {
        return NetworkServiceAdapter.getInstance(application).postCancion(newCancion, albumId)
    }
}