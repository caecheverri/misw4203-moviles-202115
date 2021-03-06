package com.sinapsis.vinilos.models.repositories
import android.app.Application
import com.android.volley.VolleyError
import com.sinapsis.vinilos.models.ColeccionistaFav
import com.sinapsis.vinilos.models.servicesadapters.NetworkServiceAdapter

class ColeccionistaFavRepository(val application: Application){
    /**
     * Invoca el servicio del adaptador que retorna favoritos de un coleccionista
     */
    suspend  fun getColeccionistaFav(favId: Int):List<ColeccionistaFav> {

        return NetworkServiceAdapter.getInstance(application).getColeccionistaFav(favId)

    }

}
