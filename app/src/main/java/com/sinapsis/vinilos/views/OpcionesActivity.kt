package com.sinapsis.vinilos.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sinapsis.vinilos.databinding.ActivityOpcionesBinding

class OpcionesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOpcionesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOpcionesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}