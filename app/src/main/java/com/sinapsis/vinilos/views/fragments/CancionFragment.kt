package com.sinapsis.vinilos.views.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sinapsis.vinilos.R
import com.sinapsis.vinilos.databinding.FragmentAlbumBinding
import com.sinapsis.vinilos.databinding.FragmentCancionBinding
import com.sinapsis.vinilos.models.Cancion
import com.sinapsis.vinilos.viewmodels.CancionViewModel
import com.sinapsis.vinilos.views.AddCancion
import com.sinapsis.vinilos.views.adapters.AlbumAdapter
import com.sinapsis.vinilos.views.adapters.CancionAdapter
import com.squareup.picasso.Picasso

class CancionFragment(private val albumId: String, private val urlAlbum: String) : Fragment(), View.OnClickListener {

    private var _binding: FragmentCancionBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: CancionViewModel
    private var viewModelAdapter: CancionAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCancionBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModelAdapter = CancionAdapter()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity)
        viewModel = ViewModelProvider(this,
            CancionViewModel.Factory(activity.application)).get(CancionViewModel::class.java)
        viewModel.getListCancion(Integer.parseInt(albumId))
        viewModel.canciones.observe(viewLifecycleOwner, Observer<List<Cancion>> {
            it.apply {
                viewModelAdapter!!.canciones = this
            }
            Picasso.get().load(this.urlAlbum).placeholder(R.drawable.ic_album)
                .error(R.drawable.ic_album)
                .into(binding.ivImagenAlbum)
        })
        setupFabButtons()
        viewModel.eventNetworkError.observe(viewLifecycleOwner, { isNetworkError ->

            if (isNetworkError) onNetworkError()
        })
    }

    private fun setupFabButtons(){

        binding.fabMenuActions.shrink()
        binding.fabMenuActions.setOnClickListener(this)
        binding.fabAddCancion.setOnClickListener(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.rvCancion
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

    override fun onClick(view:View?){
        when (view?.id) {
            R.id.fab_menu_actions -> {
                expandOrCollapseFAB()
            }
            R.id.fab_add_cancion -> {
                val addCancion= AddCancion()
                val activity = requireNotNull(this.activity)

                activity.run{
                    startActivity(Intent(this, addCancion::class.java))
                }

            }

        }

    }

    private fun expandOrCollapseFAB() {
        if (binding.fabMenuActions.isExtended) {
            binding.fabMenuActions.shrink()
            binding.fabAddCancion.hide()

        } else {
            binding.fabMenuActions.extend()
            binding.fabAddCancion.show()
        }
    }
}