package com.sinapsis.vinilos.views.adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sinapsis.vinilos.R
import com.sinapsis.vinilos.databinding.ColeccionistaItemBinding
import com.sinapsis.vinilos.models.Coleccionista
import com.sinapsis.vinilos.views.ColeccionistaDetalle


class ColeccionistaAdapter :RecyclerView.Adapter<ColeccionistaAdapter.ColeccionistaViewHolder>() {
    var coleccionistas: List<Coleccionista> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
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
            it.ivImagenColeccionista.setImageResource(R.drawable.ic_person)
            it.coleccionista = coleccionista

        }
        holder.itemView.setOnClickListener {view ->
            val intent = Intent(view.context, ColeccionistaDetalle::class.java).apply {
                putExtra("coleccionistaId", coleccionistas[position].coleccionistaId)
            }
            view.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return coleccionistas.size
    }

}