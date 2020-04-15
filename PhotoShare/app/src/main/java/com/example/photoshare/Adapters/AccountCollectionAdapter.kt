package com.example.photoshare.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.photoshare.Activities.ViewCollectionActivity
import com.example.photoshare.R
import com.google.firebase.firestore.DocumentSnapshot


class AccountCollectionViewHolder(inflater: LayoutInflater, parent: ViewGroup, private val ctx: Context) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.collection_item, parent, false)) {
    private val Title: TextView

    init {
        Title = itemView.findViewById(R.id.collectionTitle)
    }


    fun bind(collection: DocumentSnapshot, i: Int) {
        Title.text = collection["name"].toString()
        Title.setOnClickListener {
            val intent = Intent(ctx, ViewCollectionActivity::class.java)
            intent.putExtra("privateCollectionId", collection["id"].toString())
            intent.putExtra("privateCollectionName", collection["name"].toString())
            ctx.startActivity(intent)
        }
    }

}

class AccountCollectionAdapter(private val list: ArrayList<DocumentSnapshot>, private val ctx: Context)
    : RecyclerView.Adapter<AccountCollectionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountCollectionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return AccountCollectionViewHolder(inflater, parent, ctx)
    }

    override fun onBindViewHolder(holder: AccountCollectionViewHolder, position: Int) {
        val collection = list[position]
        holder.bind(collection, position)
    }

    override fun getItemCount(): Int = list.size

}

