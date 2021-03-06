package com.example.photoshare.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.photoshare.Activities.ViewPostActivity
import com.example.photoshare.R
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
//import com.example.cse438.cse438_assignment2.R
//import com.example.cse438.cse438_assignment2.Activities.TrackInfoActivity
//import com.example.cse438.cse438_assignment2.data.Track
import com.squareup.picasso.Picasso


//define the binding for the view holder
class ImageViewHolder(inflater: LayoutInflater, parent: ViewGroup, private val ctx: Context) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.post_item, parent, false)) {
    private val imgView: ImageView
    private val username: TextView
    private val caption: TextView
    private val viewButton: Button

    private val storage = Firebase.storage

    init {
        imgView = itemView.findViewById(R.id.imagePost)
        username = itemView.findViewById(R.id.username)
        caption = itemView.findViewById(R.id.caption)
        viewButton = itemView.findViewById(R.id.viewPost)
    }


    fun bind(imagePost: DocumentSnapshot, i: Int) {
        val owner = (imagePost["owner"]) as Map<String, String>
        val imageRef = storage.getReference(imagePost["imageName"].toString())
        imageRef.downloadUrl.addOnSuccessListener {
            Picasso.get().load(it).into(imgView)

            username.text = owner["username"]
            caption.text = imagePost["caption"].toString()
            viewButton.setOnClickListener{
                val viewIntent = Intent(ctx, ViewPostActivity::class.java)
                viewIntent.putExtra("postId", imagePost.id)
                ctx.startActivity(viewIntent)
            }
        }
    }

}


//define the adapter for the recycler view
class ImageAdapter(private val list: ArrayList<DocumentSnapshot>, private val ctx: Context)
    : RecyclerView.Adapter<ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ImageViewHolder(inflater, parent, ctx)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imagePost = list[position]
        holder.bind(imagePost, position)
    }

    override fun getItemCount(): Int = list.size

}