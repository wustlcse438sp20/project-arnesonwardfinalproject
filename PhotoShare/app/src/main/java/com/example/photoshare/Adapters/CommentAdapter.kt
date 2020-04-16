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


    fun bind(player: DocumentSnapshot, i: Int) {

    }

}

class CommentAdapter(private val list: ArrayList<String>)
    : RecyclerView.Adapter<CommentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CommentViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        //val : DocumentSnapshot = list[position]

    }

    override fun getItemCount(): Int = list.size

}