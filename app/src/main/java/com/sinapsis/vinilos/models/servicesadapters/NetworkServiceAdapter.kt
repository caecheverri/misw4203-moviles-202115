package com.sinapsis.vinilos.models.servicesadapters

import android.content.Context
import com.android.volley.*
import com.android.volley.Response.Listener
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.sinapsis.vinilos.models.Album
import com.sinapsis.vinilos.models.Artista
import com.sinapsis.vinilos.models.Coleccionista
import org.json.JSONArray
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import com.google.gson.Gson
import com.sinapsis.vinilos.models.Cancion
import com.sinapsis.vinilos.models.servicesadapters.NetworkServiceAdapter.Companion.BASE_URL
import java.util.*


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
        private var instance: NetworkServiceAdapter? = null

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

    fun getListCancion(albumId:Int, onComplete:(resp:List<Cancion>)->Unit, onError: (error:VolleyError)->Unit){
        requestQueue.add(getRequest("albums/$albumId/tracks",
            { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Cancion>()
                var item:JSONObject
                for (i in 0 until resp.length()) {
                    item = resp.getJSONObject(i)
                    list.add(i, Cancion(cancionId = item.getInt("id"),
                        name = item.getString("name"),
                        duration = item.getString("duration")
                    ))
                }
                onComplete(list)
            },
            {
                onError(it)
            }))
    }

    fun getAlbums(onComplete:(resp:List<Album>)->Unit, onError: (error:VolleyError)->Unit){
        requestQueue.add(getRequest("albums",
            { response ->
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

    suspend fun getArtistas() = suspendCoroutine<List<Artista>> { cont ->
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

                cont.resume(list)
            },
            { ex ->
                cont.resumeWithException(ex)
            }))
    }

    /**

     * Invoca el servicio del API que retorna todos los coleccionistas
    */
    suspend fun getColeccionistas() = suspendCoroutine<List<Coleccionista>>{ cont->
        requestQueue.add(
            getRequest(
                "collectors",
                { response ->
                    val resp = JSONArray(response)
                    val list = mutableListOf<Coleccionista>()
                    for (i in 0 until resp.length()) {
                        val item = resp.getJSONObject(i)
                        val collector = Coleccionista(
                            coleccionistaId = item.getInt("id"),
                            nombreColeccionista = item.getString("name"),
                            telefonoColeccionista = item.getString("telephone"),
                            emailColeccionista = item.getString("email"),
                        )
                        list.add(i, collector) //se agrega a medida que se procesa la respuesta
                    }
                    cont.resume(list)
                },
                {
                    cont.resumeWithException(it)
                },
            ),
        )
    }

/**
     * Invoca el servicio del API que retorna un artista dado un id
     */
    suspend fun getArtista(artistaId:Int) = suspendCoroutine<Artista> { cont ->
        requestQueue.add(getRequest("musicians/$artistaId",
            { response ->
                val resp = JSONObject(response)
                val artista = Artista(
                    artistaId = resp.getInt("id"),
                    nombre = resp.getString("name"),
                    imagen = resp.getString("image"),
                    descripcion = resp.getString("description"),
                    fechaNacimiento = resp.getString("birthDate")
                )

                cont.resume(artista)
            },
            {ex ->
                cont.resumeWithException(ex)
            }))
    }

    /**
     * Invoca el servicio del API que retorna un artista dado un id
     */
    fun getArtista(artistaId:Int, onComplete:(resp:Artista)->Unit, onError: (error:VolleyError)->Unit){
        requestQueue.add(getRequest("musicians/$artistaId",
            { response ->
                val resp = JSONObject(response)
                val artista = Artista(
                    artistaId = resp.getInt("id"),
                    nombre = resp.getString("name"),
                    imagen = resp.getString("image"),
                    descripcion = resp.getString("description"),
                    fechaNacimiento = resp.getString("birthDate")
                )
                onComplete(artista)
            },
            {
                onError(it)
            }))
    }



    /**
     * Invoca el servicio del API que retorna todos los coleccionistas
     */
    fun getColeccionistas(onComplete: (resp: List<Coleccionista>) -> Unit, onError: (error: VolleyError) -> Unit) {
        requestQueue.add(getRequest("collectors",
            { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Coleccionista>()
                for (i in 0 until resp.length()) {
                    val item = resp.getJSONObject(i)
                    list.add(
                        i, Coleccionista(
                            coleccionistaId = item.getInt("id"),
                            nombreColeccionista = item.getString("name"),
                            telefonoColeccionista = item.getString("telephone"),
                            emailColeccionista = item.getString("email")
                        )
                    )
                }
                onComplete(list)
            },
            {
                onError(it)
            }
        ))
    }

    /**
     * Invoca el servicio del API que guarda un nuevo Album
     */
    suspend fun postAlbum(albumAdd:Album) = suspendCoroutine<Album> { cont ->
        var jsonString = "{\n" +
                "    \"name\": \""+albumAdd.name +"\",\n" +
                "    \"cover\":\""+albumAdd.cover +"\",\n" +
                "    \"description\":\""+albumAdd.description +"\",\n" +
                "    \"releaseDate\":\""+albumAdd.releaseDate +"\",\n" +
                "    \"genre\": \""+albumAdd.genre+"\",\n" +
                "    \"recordLabel\": \"" +albumAdd.recordLabel+"\"\n" +
                "\n" +
                "}"

        var newAlbumJson = JSONObject(jsonString)


        requestQueue.add(postRequest("Albums",newAlbumJson,
            { response ->
                  val album = Album(
                    albumId =response.getInt("id"),
                    name =  response.getString("name"),
                    cover = response.getString("cover"),
                    releaseDate = response.getString("releaseDate"),
                    description = response.getString("description"),
                    genre = response.getString("genre"),
                    recordLabel = response.getString("recordLabel")
                )

                cont.resume(album)
            },
            {ex ->
                cont.resumeWithException(ex)
            }))

    }


    /**
     * Realiza una petición GET al API
     */
    private fun getRequest(path:String, responseListener: Listener<String>, errorListener: Response.ErrorListener): StringRequest {
        return StringRequest(Request.Method.GET, BASE_URL+path, responseListener,errorListener)
    }

    /**
     * Realiza una petición POST al API
     */
    private fun postRequest(path: String, body: JSONObject, responseListener: Listener<JSONObject>, errorListener: Response.ErrorListener ):JsonObjectRequest{
        return  JsonObjectRequest(Request.Method.POST, BASE_URL+path, body, responseListener, errorListener)
    }

    /**
     * Realiza una petición PUT al API
     */
    private fun putRequest(path: String, body: JSONObject,  responseListener: Listener<JSONObject>, errorListener: Response.ErrorListener ):JsonObjectRequest{
        return  JsonObjectRequest(Request.Method.PUT, BASE_URL+path, body, responseListener, errorListener)
    }
}