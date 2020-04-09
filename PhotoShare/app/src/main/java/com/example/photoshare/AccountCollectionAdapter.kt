package com.example.photoshare

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class AccountCollectionViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.collection_item, parent, false)) {
    private val Title: TextView

    init {
        Title = itemView.findViewById(R.id.collectionTitle)
    }


    fun bind(collection: Collection, i: Int) {
        Title.text = collection.title
    }

}

class AccountCollectionAdapter(private val list: ArrayList<Collection>)
    : RecyclerView.Adapter<AccountCollectionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountCollectionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return AccountCollectionViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: AccountCollectionViewHolder, position: Int) {
        val collection: Collection = list[position]
        holder.bind(collection, position)
    }

    override fun getItemCount(): Int = list.size

}

