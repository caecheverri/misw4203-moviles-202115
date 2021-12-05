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
import com.sinapsis.vinilos.databinding.FragmentAlbumBinding
import com.sinapsis.vinilos.databinding.FragmentFavoritoBinding
import com.sinapsis.vinilos.models.ColeccionistaFav
import com.sinapsis.vinilos.viewmodels.ColeccionistaFavViewModel
import com.sinapsis.vinilos.views.adapters.AlbumAdapter
import com.sinapsis.vinilos.views.adapters.ColeccionistaFavAdapter


class FavoritoFragment(private val favId: Int) : Fragment() {
    private var _binding: FragmentFavoritoBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: ColeccionistaFavViewModel
    private var viewModelAdapter: ColeccionistaFavAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritoBinding.inflate(inflater, container, false)
        viewModelAdapter = ColeccionistaFavAdapter()

        val activity = requireNotNull(this.activity)

        viewModel = ViewModelProvider(this,
            ColeccionistaFavViewModel.Factory(activity.application)).get(ColeccionistaFavViewModel::class.java)
        viewModel.getColeccionistaFav(favId)

        viewModel.coleccionistaFav.observe(viewLifecycleOwner, {
            it.apply {
                viewModelAdapter!!.coleccionistasFav = this
            }
        })

        viewModel.eventNetworkError.observe(viewLifecycleOwner, { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })

        return binding.root

    }
/*
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }

        viewModel = ViewModelProvider(this, ColeccionistaFavViewModel.Factory(activity.application)).get(ColeccionistaFavViewModel::class.java)
        viewModel.coleccionistaFav.observe(viewLifecycleOwner, Observer<List<ColeccionistaFav>> {
            it.apply {
                viewModelAdapter!!.coleccionistasFav = this
            }
        })
        viewModel.eventNetworkError.observe(viewLifecycleOwner, Observer<Boolean> { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })
    }
   */

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.rvColeccionistaFav
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