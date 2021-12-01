package com.sinapsis.vinilos.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.sinapsis.vinilos.databinding.ActivityAlbumDetalleBinding
import com.sinapsis.vinilos.views.fragments.CancionFragment


class AlbumDetalle : AppCompatActivity(){
    private lateinit var binding: ActivityAlbumDetalleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlbumDetalleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        val albumId = intent.extras?.get("albumId")
        val urlAlbum = intent.extras?.get("urlAlbum")
        reemplazarFragmento(CancionFragment(albumId.toString(), urlAlbum.toString()))

    }

    private fun reemplazarFragmento(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(binding.frgContainer.id, fragment)
        transaction.commit()
    }

}