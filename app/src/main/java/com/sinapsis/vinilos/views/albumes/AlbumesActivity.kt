package com.sinapsis.vinilos.views.albumes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sinapsis.vinilos.databinding.ActivityAlbumesBinding

class AlbumesActivity : AppCompatActivity() {

    private lateinit var binding:ActivityAlbumesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlbumesBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}