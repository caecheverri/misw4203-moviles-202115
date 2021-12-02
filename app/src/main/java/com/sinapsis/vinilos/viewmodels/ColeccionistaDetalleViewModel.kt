package com.sinapsis.vinilos.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.sinapsis.vinilos.models.Coleccionista
import com.sinapsis.vinilos.models.repositories.ColeccionistaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ColeccionistaDetalleViewModel (application: Application) : AndroidViewModel(application) {
    private val _coleccionista = MutableLiveData<Coleccionista>()
    val coleccionista: LiveData<Coleccionista> get() = _coleccionista

    private var _eventNetworkError = MutableLiveData(false)
    val eventNetworkError: LiveData<Boolean> get() = _eventNetworkError
    private var _isNetworkErrorShown = MutableLiveData(false)

    val isNetworkErrorShown: LiveData<Boolean> get() = _isNetworkErrorShown

    private val albumRepository = ColeccionistaRepository(application)

    fun getColeccionista(coleccionistaId: Int) {
        try {
            viewModelScope.launch(Dispatchers.Default) {
                withContext(Dispatchers.IO) {
                    _coleccionista.postValue(albumRepository.getColeccionista(coleccionistaId))
                }
                _eventNetworkError.postValue(false)
                _isNetworkErrorShown.postValue(false)
            }
        }catch (e: Exception) {
            _eventNetworkError.value = true
        }

    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ColeccionistaDetalleViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ColeccionistaDetalleViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}