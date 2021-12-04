package com.sinapsis.vinilos.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.sinapsis.vinilos.models.Cancion
import com.sinapsis.vinilos.models.repositories.CancionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddCancionViewModel(application: Application) :  AndroidViewModel(application)  {
    private val cancionRepository = CancionRepository(application)
    private val _cancion = MutableLiveData<Cancion>()
    val cancion: LiveData<Cancion> get() = _cancion

    private var _eventNetworkError = MutableLiveData(false)
    val eventNetworkError: LiveData<Boolean> get() = _eventNetworkError
    private var _isNetworkErrorShown = MutableLiveData(false)

    val isNetworkErrorShown: LiveData<Boolean> get() = _isNetworkErrorShown

    fun addCancion(newCancion: Cancion, albumId: Int) {

       try {
            viewModelScope.launch(Dispatchers.Default) {
                    withContext(Dispatchers.IO) {
                        _cancion.postValue(cancionRepository.postCancion(newCancion, albumId))

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
            if (modelClass.isAssignableFrom(AddCancionViewModel::class.java)) {
                  @Suppress("UNCHECKED_CAST")
                return AddCancionViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}