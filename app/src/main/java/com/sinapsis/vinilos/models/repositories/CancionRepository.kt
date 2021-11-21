package com.sinapsis.vinilos.models.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.sinapsis.vinilos.models.Album
import com.sinapsis.vinilos.models.Cancion
import com.sinapsis.vinilos.models.servicesadapters.NetworkServiceAdapter

class CancionRepository (val application: Application){
    fun getListCancion(albumId: Int, callback: (List<Cancion>)->Unit, onError: (VolleyError)->Unit) {
        NetworkServiceAdapter.getInstance(application).getListCancion(
            albumId, {
                callback(it)
            },

            onError
        )
    }
}