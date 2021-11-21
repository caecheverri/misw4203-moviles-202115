package com.sinapsis.vinilos.models.repositories

import android.app.Application
import com.android.volley.VolleyError
<<<<<<< HEAD
=======
import com.sinapsis.vinilos.models.Album
>>>>>>> 9564d3d7ba13dd6f71cec8b47fd43a3b5e06cbc9
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