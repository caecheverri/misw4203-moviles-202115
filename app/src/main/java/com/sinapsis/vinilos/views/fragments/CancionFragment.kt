package com.sinapsis.vinilos.views.fragments

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
import com.sinapsis.vinilos.databinding.FragmentCancionBinding
import com.sinapsis.vinilos.viewmodels.CancionViewModel
import com.sinapsis.vinilos.views.adapters.CancionAdapter
import com.squareup.picasso.Picasso

class CancionFragment(private val albumId: String, private val urlAlbum: String) : Fragment() {

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
        viewModelAdapter = CancionAdapter()
        val activity = requireNotNull(this.activity)
        viewModel = ViewModelProvider(this,
            CancionViewModel.Factory(activity.application)).get(CancionViewModel::class.java)
        viewModel.getListCancion(Integer.parseInt(albumId))
        viewModel.canciones.observe(viewLifecycleOwner, {
            it.apply {
                viewModelAdapter!!.canciones = this
            }
            Picasso.get().load(this.urlAlbum).placeholder(R.drawable.ic_album)
                .error(R.drawable.ic_album)
                .into(binding.ivImagenAlbum)
        })
        viewModel.eventNetworkError.observe(viewLifecycleOwner, { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })
        return binding.root
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
}