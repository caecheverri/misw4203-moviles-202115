package com.sinapsis.vinilos.views.adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sinapsis.vinilos.R
import com.sinapsis.vinilos.databinding.ArtistaItemBinding
import com.sinapsis.vinilos.models.Artista
import com.sinapsis.vinilos.views.ArtistaDetalle
import com.squareup.picasso.Picasso

/**
 * Implementa el adaptador para la vista que muestra el listado de artistas
 */
class ArtistaAdapter : RecyclerView.Adapter<ArtistaAdapter.ArtistaViewHolder>() {
    var artistas: List<Artista> = emptyList()
    @SuppressLint("NotifyDataSetChanged")
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    class ArtistaViewHolder(val viewDataBinding: ArtistaItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
            companion object {
                @LayoutRes
                val LAYOUT = R.layout.artista_item
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistaViewHolder {
        val withDataBinding: ArtistaItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            ArtistaViewHolder.LAYOUT,
            parent,
            false
        )
        return ArtistaViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: ArtistaViewHolder, position: Int) {
        holder.viewDataBinding.also {
            val artista: Artista = artistas[position]
            Picasso.get().load(artista.imagen).placeholder(R.drawable.ic_person)
                .error(R.drawable.ic_person)
                .into(it.ivImagenArtista)

            it.artista = artista
        }

        holder.itemView.setOnClickListener {view ->
            val intent = Intent(view.context, ArtistaDetalle::class.java).apply {
                putExtra("artistaId", artistas[position].artistaId)
            }
            view.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return artistas.size
    }
}