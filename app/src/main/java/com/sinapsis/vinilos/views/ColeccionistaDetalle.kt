package com.sinapsis.vinilos.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.sinapsis.vinilos.R
import com.sinapsis.vinilos.databinding.ActivityColeccionistaDetalleBinding
import com.sinapsis.vinilos.viewmodels.ColeccionistaDetalleViewModel
import com.squareup.picasso.Picasso

/**
 * Implementa el Activity que se usa para mostrar el detalle
 * de un Coleccionista
 */
class ColeccionistaDetalle : AppCompatActivity() {
    private lateinit var binding: ActivityColeccionistaDetalleBinding
    private lateinit var viewModel: ColeccionistaDetalleViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityColeccionistaDetalleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val coleccionistaId = intent.extras?.get("coleccionistaId")

        viewModel = ViewModelProvider(this,
            ColeccionistaDetalleViewModel.Factory(this.application)).get(ColeccionistaDetalleViewModel::class.java)

        viewModel.coleccionista.observe(this, {
            it.apply {
                binding.tvNombreColeccionista.text = this.nombreColeccionista
                binding.tvTelefonoColeccionista.text = this.telefonoColeccionista
                binding.tvEmailColeccionista.text = this.emailColeccionista

                /*Picasso.get().load(this.imagen).placeholder(R.drawable.ic_person)
                    .error(R.drawable.ic_person)
                    .into(binding.ivImagenColeccionista)*/
            }
        })

        viewModel.eventNetworkError.observe(this, { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })

        viewModel.getColeccionista(Integer.parseInt(coleccionistaId.toString()))
    }

    /**
     * Muestra un mensaje en la pantalla cuando ocurre un error en la obtencion
     * de la informacion
     */
    private fun onNetworkError() {
        if(!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(this, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }

    /**
     * Obtiene del valor recibido la parte que indica la fecha
     */
    private fun obtenerFecha(fecha: String): String {
        val elementos: List<String> = fecha.split("T")
        return elementos[0]
    }
}