package com.sinapsis.vinilos.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sinapsis.vinilos.R
import com.sinapsis.vinilos.databinding.FragmentAlbumBinding
import com.sinapsis.vinilos.viewmodels.AlbumViewModel
import com.sinapsis.vinilos.views.adapters.AlbumAdapter
import android.content.Intent
import com.sinapsis.vinilos.views.AlbumCreacion


class AlbumFragment : Fragment() , View.OnClickListener {
    private var _binding: FragmentAlbumBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: AlbumViewModel
    private var viewModelAdapter: AlbumAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlbumBinding.inflate(inflater, container, false)
        viewModelAdapter = AlbumAdapter()
        val activity = requireNotNull(this.activity)
        viewModel = ViewModelProvider(this, AlbumViewModel.Factory(activity.application)).get(AlbumViewModel::class.java)
        viewModel.albums.observe(viewLifecycleOwner, {
            it.apply {
                viewModelAdapter!!.albums = this
            }
        })
        setupFabButtons()
        viewModel.eventNetworkError.observe(viewLifecycleOwner, Observer { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.rvAlbum
        recyclerView.layoutManager = GridLayoutManager(context, 1)
        recyclerView.adapter = viewModelAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onNetworkError() {
        if(!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }

    private fun setupFabButtons(){

        binding.fabMenuActions.shrink()
        binding.fabMenuActions.setOnClickListener(this)
        binding.fabCreacionAlbum.setOnClickListener(this)
    }
    private fun showToast(message: String) {
        Toast.makeText(recyclerView.context,message, Toast.LENGTH_SHORT).show()

    }


    override fun onClick(view:View?){
        when (view?.id) {
            R.id.fab_menu_actions -> {
                expandOrCollapseFAB()
            }
            R.id.fab_creacion_album -> {
                val albumCreacion= AlbumCreacion()
                val activity = requireNotNull(this.activity)

                activity.run{
                    startActivity(Intent(this, albumCreacion::class.java))
                }



            }

        }

    }



    private fun expandOrCollapseFAB() {
        if (binding.fabMenuActions.isExtended) {
            binding.fabMenuActions.shrink()
            binding.fabCreacionAlbum.hide()
            binding.addAlbumActionText.visibility = View.GONE

        } else {
            binding.fabMenuActions.extend()
            binding.fabCreacionAlbum.show()
            binding.addAlbumActionText.visibility = View.VISIBLE
        }
    }



}