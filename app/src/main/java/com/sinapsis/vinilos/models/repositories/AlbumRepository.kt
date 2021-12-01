package com.sinapsis.vinilos.models.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.sinapsis.vinilos.models.Album
import com.sinapsis.vinilos.models.servicesadapters.NetworkServiceAdapter
import org.json.JSONObject

class AlbumRepository (val application: Application){
    suspend fun refreshData(): List<Album> {
        return NetworkServiceAdapter.getInstance(application).getAlbums()
    }
    suspend fun postAlbum(newAlbum: Album): Album {
        return NetworkServiceAdapter.getInstance(application).postAlbum(newAlbum)
    }

}