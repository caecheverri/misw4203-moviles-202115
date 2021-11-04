package com.sinapsis.vinilos.models.servicesadapters

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.sinapsis.vinilos.models.Album
import com.sinapsis.vinilos.models.Artista
import org.json.JSONArray
import org.json.JSONObject

/**
 * Implementa el patrón Service Adapter para interactuar con el
 * API de Vinilos
 */
class NetworkServiceAdapter constructor(context: Context) {
    /**
     * Crea la instancia singleton del adaptador
     */
    companion object{
        const val BASE_URL= "https://back-vinyls-populated.herokuapp.com/"
        var instance: NetworkServiceAdapter? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: NetworkServiceAdapter(context).also {
                    instance = it
                }
            }
    }

    /**
     * Crea la cola de Volley
     */
    private val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext)
    }

    fun getAlbums(onComplete:(resp:List<Album>)->Unit, onError: (error:VolleyError)->Unit){
        requestQueue.add(getRequest("albums",
            Response.Listener<String> { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Album>()
                for (i in 0 until resp.length()) {
                    val item = resp.getJSONObject(i)
                    list.add(i, Album(albumId = item.getInt("id"),
                                      name = item.getString("name"),
                                      cover = item.getString("cover"),
                                      recordLabel = item.getString("recordLabel"),
                                      releaseDate = item.getString("releaseDate"),
                                      genre = item.getString("genre"),
                                      description = item.getString("description")))
                }
                onComplete(list)
            },
            {
                onError(it)
            }))
    }

    /**
     * Invoca el servicio del API que retorna todos los artistas
     */
    fun getArtistas(onComplete:(resp:List<Artista>)->Unit, onError: (error:VolleyError)->Unit){
        requestQueue.add(getRequest("musicians",
            { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Artista>()
                for (i in 0 until resp.length()) {
                    val item = resp.getJSONObject(i)
                    list.add(i, Artista(
                        artistaId = item.getInt("id"),
                        nombre = item.getString("name"),
                        imagen = item.getString("image")))
                }
                onComplete(list)
            },
            {
                onError(it)
            }))
    }

    /**
     * Realiza una petición GET al API
     */
    private fun getRequest(path:String, responseListener: Response.Listener<String>, errorListener: Response.ErrorListener): StringRequest {
        return StringRequest(Request.Method.GET, BASE_URL+path, responseListener,errorListener)
    }

    /**
     * Realiza una petición POST al API
     */
    private fun postRequest(path: String, body: JSONObject,  responseListener: Response.Listener<JSONObject>, errorListener: Response.ErrorListener ):JsonObjectRequest{
        return  JsonObjectRequest(Request.Method.POST, BASE_URL+path, body, responseListener, errorListener)
    }

    /**
     * Realiza una petición PUT al API
     */
    private fun putRequest(path: String, body: JSONObject,  responseListener: Response.Listener<JSONObject>, errorListener: Response.ErrorListener ):JsonObjectRequest{
        return  JsonObjectRequest(Request.Method.PUT, BASE_URL+path, body, responseListener, errorListener)
    }
}