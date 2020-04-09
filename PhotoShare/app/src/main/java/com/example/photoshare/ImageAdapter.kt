package com.example.photoshare

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
//import com.example.cse438.cse438_assignment2.R
//import com.example.cse438.cse438_assignment2.Activities.TrackInfoActivity
//import com.example.cse438.cse438_assignment2.data.Track
import com.squareup.picasso.Picasso


//define the binding for the view holder
class ImageViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.post_item, parent, false)) {
    private val imgView: ImageView
    private val username: TextView
    private val caption: TextView

    init {
        imgView = itemView.findViewById(R.id.imagePost)
        username = itemView.findViewById(R.id.username)
        caption = itemView.findViewById(R.id.caption)
    }


    fun bind(imagePost: ImagePost, i: Int) {
        // picasso here
        Picasso.get().load("https://i.kym-cdn.com/entries/icons/original/000/033/233/cover8.jpg").into(imgView)
        username.text = imagePost.email
        caption.text = imagePost.caption

//        imgView.setOnClickListener {
//            val intent = Intent(it.context, ViewPostActivity::class.java).apply {
//                putExtra("postIndex", i)
//            }
//            it.context.startActivity(intent)
//        }
    }

}


//define the adapter for the recycler view
class ImageAdapter(private val list: ArrayList<ImagePost>)
    : RecyclerView.Adapter<ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ImageViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imagePost: ImagePost = list[position]
        holder.bind(imagePost, position)
    }

    override fun getItemCount(): Int = list.size

}