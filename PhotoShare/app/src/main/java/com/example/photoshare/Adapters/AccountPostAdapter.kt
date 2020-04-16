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
import com.squareup.picasso.Picasso


class AccountPostViewHolder(inflater: LayoutInflater, parent: ViewGroup, private val ctx: Context) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.account_post_item, parent, false)) {
    private val img: ImageView = itemView.findViewById(R.id.accountPostImagePreview)
//    private val wrapper: ViewGroup = itemView.findViewById(R.id.collectionItemWrapper)


    fun bind(collection: DocumentSnapshot, i: Int) {
        Picasso.get().load("https://engineering.wustl.edu/Profiles/PublishingImages/Cytron_Ron_2016.jpg?RenditionID=6").into(img)
        img.setOnClickListener {
//            val intent = Intent(ctx, ViewCollectionActivity::class.java)
//            intent.putExtra("privateCollectionId", collection["id"].toString())
//            intent.putExtra("privateCollectionName", collection["name"].toString())
//            ctx.startActivity(intent)
        }
    }

}

class AccountPostAdapter(private val list: ArrayList<DocumentSnapshot>, private val ctx: Context)
    : RecyclerView.Adapter<AccountPostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountPostViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return AccountPostViewHolder(inflater, parent, ctx)
    }

    override fun onBindViewHolder(holder: AccountPostViewHolder, position: Int) {
        val collection = list[position]
        holder.bind(collection, position)
    }

    override fun getItemCount(): Int = list.size

}

