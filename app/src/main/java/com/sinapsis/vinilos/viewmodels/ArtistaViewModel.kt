package com.sinapsis.vinilos.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.sinapsis.vinilos.models.Artista
import com.sinapsis.vinilos.models.repositories.ArtistaRepository

/**
 * Implementa el ViewModel para las funcionalidades de Artista
 */
class ArtistaViewModel (application: Application) : AndroidViewModel(application) {
    private val _artistas = MutableLiveData<List<Artista>>()
    val artistas: LiveData<List<Artista>> get() = _artistas

    private var _eventNetworkError = MutableLiveData(false)
    val eventNetworkError: LiveData<Boolean> get() = _eventNetworkError
    private var _isNetworkErrorShown = MutableLiveData(false)

    val isNetworkErrorShown: LiveData<Boolean> get() = _isNetworkErrorShown

    private val albumRepository = ArtistaRepository(application)

    init {
        refreshDataFromNetwork()
    }

    private fun refreshDataFromNetwork() {
        albumRepository.getArtistas({list ->
            _artistas.postValue(list)
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
            if (modelClass.isAssignableFrom(ArtistaViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ArtistaViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}