package com.sinapsis.vinilos.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sinapsis.vinilos.R
import com.sinapsis.vinilos.databinding.AlbumItemBinding
import com.sinapsis.vinilos.databinding.ArtistaItemBinding
import com.sinapsis.vinilos.databinding.ColeccionistaItemBinding
import com.sinapsis.vinilos.models.Artista
import com.sinapsis.vinilos.models.Coleccionista
import com.squareup.picasso.Picasso

class ColeccionistaAdapter :RecyclerView.Adapter<ColeccionistaAdapter.ColeccionistaViewHolder>() {
    var coleccionistas: List<Coleccionista> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ColeccionistaViewHolder(val viewDataBinding: ColeccionistaItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.coleccionista_item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColeccionistaViewHolder {
        val withDataBinding: ColeccionistaItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            ColeccionistaViewHolder.LAYOUT,
            parent,
            false
        )
        return ColeccionistaViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: ColeccionistaViewHolder, position: Int) {
        holder.viewDataBinding.also {
            val coleccionista: Coleccionista = coleccionistas[position]
            Picasso.get().load(coleccionista.coleccionistaId).placeholder(R.drawable.ic_person)
                .error(R.drawable.ic_person)
                .into(it.ivImagenColeccionista)

            it.coleccionista = coleccionista
        }
    }

    override fun getItemCount(): Int {
        return coleccionistas.size
    }

}