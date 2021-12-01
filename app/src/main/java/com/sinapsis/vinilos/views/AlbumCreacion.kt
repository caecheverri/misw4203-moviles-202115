package com.sinapsis.vinilos.views

import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.lifecycle.viewModelScope
import com.android.volley.Response
import com.google.android.apps.common.testing.accessibility.framework.replacements.TextUtils
import com.google.gson.Gson
import com.sinapsis.vinilos.R
import com.sinapsis.vinilos.databinding.ActivityAlbumCrearBinding
import com.sinapsis.vinilos.models.Album
import com.sinapsis.vinilos.viewmodels.AlbumCreacionViewModel

import com.squareup.picasso.Picasso
import org.json.JSONObject

class AlbumCreacion : AppCompatActivity() {
    private lateinit var binding: ActivityAlbumCrearBinding
    private lateinit var viewModel: AlbumCreacionViewModel
    private val loadingPB: ProgressBar? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlbumCrearBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()


        viewModel = ViewModelProvider(this,
            AlbumCreacionViewModel.Factory(this.application)).get(AlbumCreacionViewModel::class.java)



        binding.idBttnAceptar.setOnClickListener {

            var nombreAlbum = binding.txtNombreAlbum.text.toString()
            var coverAlbum = binding.txtCoverAlbum.text.toString()
            var descripcionAlbum = binding.txtDescAlbum.text.toString()
            var fecLanzamientoAlbum = binding.txtAnioLanz.text.toString()
            var genreAlbum = binding.txtGeneroAlbum.text.toString()
            var recordLabel = binding.txtRecordLabel.text.toString()

            var newAlbum = Album(
                0,
                nombreAlbum,
                coverAlbum,
                fecLanzamientoAlbum,
                descripcionAlbum,
                genreAlbum,
                recordLabel
            )
            var banderaLleno = true

            if(TextUtils.isEmpty(nombreAlbum))
            {
                banderaLleno=false
            }else if (TextUtils.isEmpty(coverAlbum))
            {
                banderaLleno=false
            }else if (TextUtils.isEmpty(fecLanzamientoAlbum))
            {
                banderaLleno=false
            }else if (TextUtils.isEmpty(descripcionAlbum))
            {
                banderaLleno=false
            }else if (TextUtils.isEmpty(genreAlbum))
            {
                banderaLleno=false
            }else if (TextUtils.isEmpty(recordLabel))
            {
                banderaLleno=false
            }

            if(banderaLleno)
            {
                try {
                    viewModel.addAlbum(newAlbum)

                    viewModel.album.observe(this, {
                        it.apply {
                            var albumId = this.albumId

                            if(albumId == 0)
                            {
                                showToast("Error al agregar el Album")
                            }
                            else
                            {
                                showToast("Album Agregado "  + this.albumId)
                                finish()
                            }


                        }
                    })
                }
                catch (e:Exception){
                    showToast("Error al agregar el Album " + e.message.toString())
                }

            }
            else
            {
                showToast("Datos Incompletos")

            }

        }

        viewModel.eventNetworkError.observe(this, { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })

    }


    private fun showToast(message: String) {
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show()

    }

    /**
     * Muestra un mensaje en la pantalla cuando ocurre un error en la obtencion
     * de la informacion
     */
    private fun onNetworkError() {
        if(!viewModel.isNetworkErrorShown.value!!) {
            showToast("Network Error")
            viewModel.onNetworkErrorShown()
        }
    }
}