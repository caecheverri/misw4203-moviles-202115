package com.sinapsis.vinilos.views

import android.os.Bundle
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.google.android.apps.common.testing.accessibility.framework.replacements.TextUtils
import com.sinapsis.vinilos.databinding.ActivityAddCancionBinding
import com.sinapsis.vinilos.models.Album
import com.sinapsis.vinilos.models.Cancion
import com.sinapsis.vinilos.viewmodels.AddCancionViewModel


class AddCancion : AppCompatActivity()  {
    private lateinit var binding: ActivityAddCancionBinding
    private lateinit var viewModel: AddCancionViewModel
    private val loadingPB: ProgressBar? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCancionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()


        viewModel = ViewModelProvider(this,
            AddCancionViewModel.Factory(this.application)).get(AddCancionViewModel::class.java)

        val albumId = 100

        binding.idBttnGuardar.setOnClickListener {

            var name = binding.txtNombreCancion.text.toString()
            var duration = binding.txtDuracionCancion.text.toString()


            var newCancion = Cancion(
                name,
                duration
            )
            var banderaLleno = true

            if(TextUtils.isEmpty(name))
            {
                banderaLleno=false
            }else if (TextUtils.isEmpty(duration)) {
                banderaLleno = false
            }
            if(banderaLleno)
            {
                try {
                    viewModel.addCancion(newCancion, Integer.parseInt(albumId.toString()))

                    viewModel.cancion.observe(this, {
                        it.apply {
                                showToast("Canción Agregada ")
                                finish()
                        }
                    })
                }
                catch (e:Exception){
                    showToast("Error al agregar la Canción al Album " + e.message.toString())
                }

            }
            else
            {
                showToast("Datos Incompletos")

            }

        }

        binding.idBttnCancelar.setOnClickListener {
            showToast("Aderir Canción Cancelada")
            finish()
        }

        viewModel.eventNetworkError.observe(this, { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })

    }


    private fun showToast(message: String) {
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show()

    }

    private fun onNetworkError() {
        if(!viewModel.isNetworkErrorShown.value!!) {
            showToast("Network Error")
            viewModel.onNetworkErrorShown()
        }
    }
}