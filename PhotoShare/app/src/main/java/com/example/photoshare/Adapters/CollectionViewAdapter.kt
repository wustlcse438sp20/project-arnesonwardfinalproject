package com.example.photoshare.Adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.photoshare.R
import com.squareup.picasso.Picasso


class CollectionViewViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.collection_view_item, parent, false)) {
    private val image: ImageView


    init {
        image = itemView.findViewById(R.id.collectionItem)
    }


    fun bind(url: Uri, i: Int) {
        Picasso.get().load(url).into(image)
    }

}

class CollectionViewAdapter(private val list: ArrayList<Uri>)
    : RecyclerView.Adapter<CollectionViewViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionViewViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CollectionViewViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: CollectionViewViewHolder, position: Int) {
        val url = list[position]
        holder.bind(url, position)
    }

    override fun getItemCount(): Int = list.size

}

