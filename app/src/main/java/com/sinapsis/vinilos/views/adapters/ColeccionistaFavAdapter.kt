package com.sinapsis.vinilos.views.adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sinapsis.vinilos.R
import com.sinapsis.vinilos.databinding.FavoritoItemBinding
import com.sinapsis.vinilos.models.ColeccionistaFav
import com.sinapsis.vinilos.views.ColeccionistaDetalle
import com.squareup.picasso.Picasso

class ColeccionistaFavAdapter :RecyclerView.Adapter<ColeccionistaFavAdapter.ColeccionistaViewFavHolder>() {

    var coleccionistasFav: List<ColeccionistaFav> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ColeccionistaViewFavHolder(val viewDataBinding: FavoritoItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.coleccionista_item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColeccionistaViewFavHolder {
        val withDataBinding: FavoritoItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            ColeccionistaViewFavHolder.LAYOUT,
            parent,
            false
        )
        return ColeccionistaViewFavHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: ColeccionistaViewFavHolder, position: Int) {
        holder.viewDataBinding.also {
            val coleccionistaFav  : ColeccionistaFav = coleccionistasFav[position]
            Picasso.get().load(coleccionistaFav.imagenFav).placeholder(R.drawable.ic_person)
                .error(R.drawable.ic_person)
                .into(it.ivImagenFav)
            it.favorito = coleccionistaFav
        }


        holder.itemView.setOnClickListener {view ->
            val intent = Intent(view.context, ColeccionistaDetalle::class.java).apply {
                putExtra("favId", coleccionistasFav[position].favId)
            }
            view.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return coleccionistasFav.size
    }

}