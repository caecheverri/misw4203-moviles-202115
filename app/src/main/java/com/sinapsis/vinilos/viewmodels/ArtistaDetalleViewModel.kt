package com.sinapsis.vinilos.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.sinapsis.vinilos.models.Artista
import com.sinapsis.vinilos.models.repositories.ArtistaRepository

class ArtistaDetalleViewModel (application: Application) : AndroidViewModel(application) {
    private val _artista = MutableLiveData<Artista>()
    val artista: LiveData<Artista> get() = _artista

    private var _eventNetworkError = MutableLiveData(false)
    val eventNetworkError: LiveData<Boolean> get() = _eventNetworkError
    private var _isNetworkErrorShown = MutableLiveData(false)

    val isNetworkErrorShown: LiveData<Boolean> get() = _isNetworkErrorShown

    private val albumRepository = ArtistaRepository(application)

    fun getArtista(artistaId: Int) {
        albumRepository.getArtista(artistaId, {response ->
            _artista.postValue(response)
            _eventNetworkError.value = false
            _isNetworkErrorShown.value = false
        },{
            _eventNetworkError.value = true
        })
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ArtistaDetalleViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ArtistaDetalleViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}