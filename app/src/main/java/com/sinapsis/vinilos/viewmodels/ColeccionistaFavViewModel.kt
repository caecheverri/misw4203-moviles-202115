package com.sinapsis.vinilos.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.sinapsis.vinilos.models.ColeccionistaFav
import com.sinapsis.vinilos.models.repositories.ColeccionistaFavRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ColeccionistaFavViewModel (application: Application) : AndroidViewModel(application) {

    private val _ColeccionistaFav = ColeccionistaFavRepository(application)
    private val _coleccionista = MutableLiveData<List<ColeccionistaFav>>()
    val coleccionistaFav: LiveData<List<ColeccionistaFav>> get() = _coleccionista

    private var _eventNetworkError = MutableLiveData(false)
    val eventNetworkError: LiveData<Boolean> get() = _eventNetworkError
    private var _isNetworkErrorShown = MutableLiveData(false)

    val isNetworkErrorShown: LiveData<Boolean> get() = _isNetworkErrorShown

    fun getColeccionistaFav(coleccionistaId:Int) {
        try {
            viewModelScope.launch(Dispatchers.Default) {
                withContext(Dispatchers.IO) {
                        _coleccionista.postValue(_ColeccionistaFav.getColeccionistaFav(coleccionistaId))

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
            if (modelClass.isAssignableFrom(ColeccionistaFavViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ColeccionistaFavViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
