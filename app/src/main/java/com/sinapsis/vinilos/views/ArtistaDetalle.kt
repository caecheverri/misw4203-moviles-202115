package com.sinapsis.vinilos.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.sinapsis.vinilos.R
import com.sinapsis.vinilos.databinding.ActivityArtistaDetalleBinding
import com.sinapsis.vinilos.viewmodels.ArtistaDetalleViewModel
import com.squareup.picasso.Picasso

/**
 * Implementa el Activity que se usa para mostrar el detalle
 * de un artista
 */
class ArtistaDetalle : AppCompatActivity() {
    private lateinit var binding: ActivityArtistaDetalleBinding
    private lateinit var viewModel: ArtistaDetalleViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArtistaDetalleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val artistaId = intent.extras?.get("artistaId")

        viewModel = ViewModelProvider(this,
            ArtistaDetalleViewModel.Factory(this.application)).get(ArtistaDetalleViewModel::class.java)

        viewModel.artista.observe(this, {
            it.apply {
                binding.tvNombreArtista.text = this.nombre
                binding.tvDescripcion.text = this.descripcion
                binding.tvFechaNacimiento.text = obtenerFecha(this.fechaNacimiento)

                Picasso.get().load(this.imagen).placeholder(R.drawable.ic_person)
                    .error(R.drawable.ic_person)
                    .into(binding.ivImagenArtista)
            }
        })

        viewModel.eventNetworkError.observe(this, { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })

        viewModel.getArtista(Integer.parseInt(artistaId.toString()))
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