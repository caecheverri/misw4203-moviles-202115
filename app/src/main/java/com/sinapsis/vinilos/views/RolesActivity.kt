package com.sinapsis.vinilos.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sinapsis.vinilos.databinding.ActivityRolesBinding
import com.sinapsis.vinilos.views.albumes.AlbumesActivity

class RolesActivity : AppCompatActivity() {

    private lateinit var binding:ActivityRolesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRolesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btColeccionista.setOnClickListener {
            val intent = Intent(this, AlbumesActivity::class.java)
            startActivity(intent)
        }
    }
}