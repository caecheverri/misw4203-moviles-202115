package com.sinapsis.vinilos.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sinapsis.vinilos.databinding.FragmentColeccionistaBinding
import com.sinapsis.vinilos.viewmodels.ColeccionistaViewModel
import com.sinapsis.vinilos.views.adapters.ColeccionistaAdapter


class ColeccionistaFragment : Fragment() {
    private var _binding: FragmentColeccionistaBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: ColeccionistaViewModel
    private var viewModelAdapter: ColeccionistaAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentColeccionistaBinding.inflate(inflater, container, false)
        viewModelAdapter = ColeccionistaAdapter()

        val activity = requireNotNull(this.activity)

        viewModel = ViewModelProvider(this,
            ColeccionistaViewModel.Factory(activity.application)).get(ColeccionistaViewModel::class.java)

        viewModel.coleccionistas.observe(viewLifecycleOwner, {
            it.apply {
                viewModelAdapter!!.coleccionistas = this
            }
        })

        viewModel.eventNetworkError.observe(viewLifecycleOwner, { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.rvColeccionista
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