package com.example.photoshare.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.photoshare.R
import com.google.firebase.firestore.DocumentSnapshot
import kotlin.math.roundToInt

class CommentViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.comment_item, parent, false)) {

    private val userTextView: TextView = itemView.findViewById(R.id.commenterUsername)
    private val contentTextView: TextView = itemView.findViewById(R.id.commentContent)


    fun bind(comment: DocumentSnapshot, i: Int) {
        userTextView.text = comment["commenterName"].toString()
        contentTextView.text = comment["text"].toString()
    }

}

class CommentAdapter(private val list: ArrayList<DocumentSnapshot>)
    : RecyclerView.Adapter<CommentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CommentViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(list[position], position)

    }

    override fun getItemCount(): Int = list.size

}