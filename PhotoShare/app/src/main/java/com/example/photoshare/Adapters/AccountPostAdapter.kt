package com.example.photoshare.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.photoshare.Activities.NewPostActivity
import com.example.photoshare.Activities.ViewCollectionActivity
import com.example.photoshare.R
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.squareup.picasso.Picasso


class AccountPostViewHolder(inflater: LayoutInflater, parent: ViewGroup, private val ctx: Context) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.account_post_item, parent, false)) {
    private val img: ImageView = itemView.findViewById(R.id.accountPostImagePreview)
    private val storage = Firebase.storage


    fun bind(post: DocumentSnapshot, i: Int) {
        val imageRef = storage.getReference(post["imageName"].toString())
        imageRef.downloadUrl.addOnSuccessListener {
            Picasso.get().load(it).into(img)
            img.setOnClickListener {
                val intent = Intent(ctx, NewPostActivity::class.java)
                intent.putExtra("editPostRefPath", post.reference.path)
                ctx.startActivity(intent)
            }
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
        val post = list[position]
        holder.bind(post, position)
    }

    override fun getItemCount(): Int = list.size

}

