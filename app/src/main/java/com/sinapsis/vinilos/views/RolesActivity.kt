package com.sinapsis.vinilos.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sinapsis.vinilos.databinding.ActivityRolesBinding

class RolesActivity : AppCompatActivity() {

    private lateinit var binding:ActivityRolesBinding

    /**
     * Inicializa los elementos de la actividad
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRolesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //Evento que invoca el activity de las opciones de listados
        binding.btColeccionista.setOnClickListener {
            val intent = Intent(this, OpcionesActivity::class.java)
            startActivity(intent)
        }
    }

    /**
     * Sobreescribe le función que habilita la navegación hacia atras
     */
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}