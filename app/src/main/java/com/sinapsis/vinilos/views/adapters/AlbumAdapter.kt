package com.sinapsis.vinilos.views.adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sinapsis.vinilos.R
import com.sinapsis.vinilos.databinding.AlbumItemBinding
import com.sinapsis.vinilos.models.Album
import com.sinapsis.vinilos.views.AlbumDetalle
import com.squareup.picasso.Picasso
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class AlbumAdapter : RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>(){

    var albums :List<Album> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val withDataBinding: AlbumItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            AlbumViewHolder.LAYOUT,
            parent,
            false)
        return AlbumViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.viewDataBinding.also {
            val album = albums[position]
            Picasso.get().load(album.cover).placeholder(R.drawable.ic_person)
                .error(R.drawable.ic_person)
                .into(it.ivImagenAlbum)
            it.album = album
        }

        holder.itemView.setOnClickListener {view ->
            val intent = Intent(view.context, AlbumDetalle::class.java).apply {
                putExtra("albumId", albums[position].albumId)
                putExtra("urlAlbum", albums[position].cover)
            }
            view.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return albums.size
    }


    class AlbumViewHolder(val viewDataBinding: AlbumItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.album_item
        }
        fun bind(album: Album) {
            Glide.with(itemView)
                .load(album.cover.toUri().buildUpon().scheme("https").build())
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.loading_animation)

                        .error(R.drawable.ic_broken_image))
                .into(viewDataBinding.ivImagenAlbum)
        }
    }


}