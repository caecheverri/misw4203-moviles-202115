package com.sinapsis.vinilos.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.google.gson.Gson
import com.sinapsis.vinilos.models.Album
import com.sinapsis.vinilos.models.repositories.AlbumRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject

class AlbumCreacionViewModel(application: Application) :  AndroidViewModel(application) {
    private val albumRepository = AlbumRepository(application)
    private val _album = MutableLiveData<Album>()
    val album: LiveData<Album> get() = _album

    private var _eventNetworkError = MutableLiveData(false)
    val eventNetworkError: LiveData<Boolean> get() = _eventNetworkError
    private var _isNetworkErrorShown = MutableLiveData(false)

    val isNetworkErrorShown: LiveData<Boolean> get() = _isNetworkErrorShown

    fun addAlbum(newAlbum: Album) {

        try {
            viewModelScope.launch(Dispatchers.Default) {
                withContext(Dispatchers.IO) {
                    _album.postValue(albumRepository.postAlbum(newAlbum))

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
            if (modelClass.isAssignableFrom(AlbumCreacionViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AlbumCreacionViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }



}