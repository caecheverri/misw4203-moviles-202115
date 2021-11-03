package com.sinapsis.vinilos.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.sinapsis.vinilos.R
import com.sinapsis.vinilos.databinding.ActivityOpcionesBinding
import com.sinapsis.vinilos.views.fragments.ArtistaFragment
import com.sinapsis.vinilos.views.fragments.ColeccionistaFragment

/**
 * Implementa el Activity que se reutiliza para renderizar los
 * fragmentos que muestran la información de álbumes, artistas y
 * coleccionistas
 */
class OpcionesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOpcionesBinding
    private val artistasFragment = ArtistaFragment()
    private val coleccionistaFragment = ColeccionistaFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOpcionesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        reemplazarFragmento(artistasFragment)

        binding.btmNavigation.setOnItemSelectedListener {param ->
            when(param.itemId) {
                R.id.ic_artist -> reemplazarFragmento(artistasFragment)
                R.id.ic_collector -> reemplazarFragmento(coleccionistaFragment)
            }
            true
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    /**
     * Reemplaza el fragmento mostrado en el activity de acuerdo
     * a la selección del usuario
     */
    private fun reemplazarFragmento(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(binding.fragContainer.id, fragment)
        transaction.commit()
    }
}