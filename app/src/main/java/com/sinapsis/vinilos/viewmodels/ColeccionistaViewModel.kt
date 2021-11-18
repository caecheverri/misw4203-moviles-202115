package com.sinapsis.vinilos.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.sinapsis.vinilos.models.Coleccionista
import com.sinapsis.vinilos.models.repositories.ColeccionistaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

/**
 * Implementa el ViewModel para las funcionalidades de Coleccionista
 */
class ColeccionistaViewModel(application: Application) : AndroidViewModel(application) {

    private val coleccionistasRepository = ColeccionistaRepository(application)
    private val _coleccionistas = MutableLiveData<List<Coleccionista>>()

    val coleccionistas: LiveData<List<Coleccionista>> get()= _coleccionistas

    private var _eventNetworkError = MutableLiveData(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    init {
        refreshDataFromNetwork()
    }
/**
    private fun refreshDataFromNetwork() {
        coleccionistasRepository.getColeccionistas({ list ->
            _coleccionistas.postValue(list)
            _eventNetworkError.value = false
            _isNetworkErrorShown.value = false
        },{
            _eventNetworkError.value = true
        })
    }
*/

    private fun refreshDataFromNetwork() {
        try {
            viewModelScope.launch (Dispatchers.Default){
                withContext(Dispatchers.IO){
                    var data = coleccionistasRepository.getColeccionistas()
                    _coleccionistas.postValue(data)
                }
                _eventNetworkError.postValue(false)
                _isNetworkErrorShown.postValue(false)
            }
        }
        catch (e:Exception){
            _eventNetworkError.value = true
        }
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ColeccionistaViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ColeccionistaViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}