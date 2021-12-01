package com.sinapsis.vinilos.views.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sinapsis.vinilos.R
import com.sinapsis.vinilos.databinding.CancionItemBinding
import com.sinapsis.vinilos.models.Cancion


class CancionAdapter : RecyclerView.Adapter<CancionAdapter.CancionViewHolder>() {

    var canciones :List<Cancion> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CancionViewHolder {
        val withDataBinding: CancionItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            CancionViewHolder.LAYOUT,
            parent,
            false)
        return CancionViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: CancionViewHolder, position: Int) {
        holder.viewDataBinding.also {
            val cancion = canciones[position]
            it.cancion = cancion
        }
    }

    override fun getItemCount(): Int {
        return canciones.size
    }


    class CancionViewHolder(val viewDataBinding: CancionItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.cancion_item
        }
    }
}