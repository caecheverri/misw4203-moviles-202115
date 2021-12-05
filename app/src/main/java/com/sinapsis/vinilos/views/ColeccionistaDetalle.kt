package com.sinapsis.vinilos.views

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.sinapsis.vinilos.databinding.ActivityColeccionistaDetalleBinding
import com.sinapsis.vinilos.viewmodels.ColeccionistaDetalleViewModel
import com.sinapsis.vinilos.viewmodels.ColeccionistaFavViewModel
import com.sinapsis.vinilos.views.fragments.CancionFragment
import com.sinapsis.vinilos.views.fragments.ColeccionistaFragment
import com.sinapsis.vinilos.views.fragments.FavoritoFragment
import com.squareup.picasso.Picasso

/**
 * Implementa el Activity que se usa para mostrar el detalle
 * de un Coleccionista
 */
class ColeccionistaDetalle : AppCompatActivity() {
    private lateinit var binding: ActivityColeccionistaDetalleBinding

    private lateinit var viewModel:ColeccionistaDetalleViewModel
    private lateinit var viewModelfav:ColeccionistaFavViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityColeccionistaDetalleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        val coleccionistaId = intent.extras?.get("coleccionistaId")
        val favId = intent.extras?.get("favId")

        viewModel = ViewModelProvider(this,ColeccionistaDetalleViewModel.Factory(this.application)).get(ColeccionistaDetalleViewModel::class.java)
        viewModelfav = ViewModelProvider(this,ColeccionistaFavViewModel.Factory(this.application)).get(ColeccionistaFavViewModel::class.java)

        viewModel.coleccionista.observe(this, {
            it.apply {
                binding.tvNombreColeccionista.text = this.nombreColeccionista
                binding.tvTelefonoColeccionista.text = this.telefonoColeccionista
                binding.tvEmailColeccionista.text = this.emailColeccionista
            }
        })


        viewModel.eventNetworkError.observe(this, { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })

        viewModel.getColeccionista(Integer.parseInt(coleccionistaId.toString()))
        viewModelfav.getColeccionistaFav(Integer.parseInt(coleccionistaId.toString()))

        reemplazarFragmento(FavoritoFragment(Integer.parseInt(coleccionistaId.toString())))
    }

    private fun reemplazarFragmento(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(binding.frgContainerFav.id, fragment)
        transaction.commit()
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

}