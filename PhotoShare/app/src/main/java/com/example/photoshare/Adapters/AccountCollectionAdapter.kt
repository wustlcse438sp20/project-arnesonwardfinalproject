package com.example.photoshare.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.photoshare.Activities.ViewCollectionActivity
import com.example.photoshare.R
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.squareup.picasso.Picasso


class AccountCollectionViewHolder(inflater: LayoutInflater, parent: ViewGroup, private val ctx: Context) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.collection_item, parent, false)) {
    private val Title: TextView = itemView.findViewById(R.id.collectionTitle)
    private val img: ImageView = itemView.findViewById(R.id.collectionImagePreview)
    private val storage = Firebase.storage
//    private val wrapper: ViewGroup = itemView.findViewById(R.id.collectionItemWrapper)


    fun bind(collection: DocumentSnapshot, i: Int) {
        Title.text = collection["name"].toString()

        val colRef = storage.getReference("privateCollections/" + collection["id"])
        colRef.listAll()
            .addOnSuccessListener {
                if (it.items.isNotEmpty()) {
                    it.items[0].downloadUrl.addOnSuccessListener {
                        Picasso.get().load(it).into(img)
                    }
                }
                else {
                    // Load default image if collection is empty
                    Picasso.get().load("https://media.istockphoto.com/vectors/instant-pictures-vector-id527031569?k=6&m=527031569&s=612x612&w=0&h=zccUCWznzNfhwTNk1spGtFD3rH2zAYXNFChibbkM_uw=").into(img)
                }
            }

        itemView.setOnClickListener {
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

