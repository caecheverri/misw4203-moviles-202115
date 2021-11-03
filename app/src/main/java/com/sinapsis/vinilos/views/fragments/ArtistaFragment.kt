package com.sinapsis.vinilos.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.sinapsis.vinilos.R
import com.sinapsis.vinilos.databinding.FragmentArtistaBinding
import com.sinapsis.vinilos.models.Artista
import com.sinapsis.vinilos.viewmodels.ArtistaViewModel
import com.sinapsis.vinilos.views.adapters.ArtistaAdapter

/**
 * Implementa el fragmento usado para mostrar el listado de artistas
 */
class ArtistaFragment : Fragment() {
    private var _binding: FragmentArtistaBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: ArtistaViewModel
    private var viewModelAdapter: ArtistaAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArtistaBinding.inflate(inflater, container, false)
        viewModelAdapter = ArtistaAdapter()

        val activity = requireNotNull(this.activity)

        viewModel = ViewModelProvider(this,
            ArtistaViewModel.Factory(activity.application)).get(ArtistaViewModel::class.java)

        viewModel.artistas.observe(viewLifecycleOwner, {
            it.apply {
                viewModelAdapter!!.artistas = this
            }
        })

        viewModel.eventNetworkError.observe(viewLifecycleOwner, { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.rvArtista
        recyclerView.layoutManager = GridLayoutManager(context, 2)
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