package com.sinapsis.vinilos.models.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.sinapsis.vinilos.models.Album
import com.sinapsis.vinilos.models.servicesadapters.NetworkServiceAdapter

class AlbumRepository (val application: Application){
    fun refreshData(callback: (List<Album>)->Unit, onError: (VolleyError)->Unit) {
        NetworkServiceAdapter.getInstance(application).getAlbums({
            callback(it)
        },
            onError
        )
    }
}