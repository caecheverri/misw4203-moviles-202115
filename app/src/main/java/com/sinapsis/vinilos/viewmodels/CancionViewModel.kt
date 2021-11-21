package com.sinapsis.vinilos.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.sinapsis.vinilos.models.Album
import com.sinapsis.vinilos.models.Cancion
import com.sinapsis.vinilos.models.repositories.AlbumRepository
import com.sinapsis.vinilos.models.repositories.CancionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CancionViewModel(application: Application) :  AndroidViewModel(application){
    private val cancionRepository = CancionRepository(application)

    private val _canciones = MutableLiveData<List<Cancion>>()

    val canciones: LiveData<List<Cancion>>
        get() = _canciones

    private var _eventNetworkError = MutableLiveData(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    fun getListCancion(albumId: Int) {
        try {
            cancionRepository.getListCancion(albumId, {
                _canciones.postValue(it)
                _eventNetworkError.value = false
                _isNetworkErrorShown.value = false
            },{
                _eventNetworkError.value = true
            })
        } catch (e: Exception) {
            _eventNetworkError.value = true
        }
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CancionViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CancionViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}