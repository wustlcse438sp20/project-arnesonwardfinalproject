package com.example.photoshare.Adapters

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.photoshare.Activities.ViewPostActivity
import com.example.photoshare.R
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlin.math.roundToInt

class CommentViewHolder(inflater: LayoutInflater, parent: ViewGroup, private val UID2: String, private val refreshWithThis: ViewPostActivity) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.comment_item, parent, false)) {

    private val storage = Firebase.storage
    private val db = Firebase.firestore
    private val userTextView: TextView = itemView.findViewById(R.id.commenterUsername)
    private val contentTextView: TextView = itemView.findViewById(R.id.commentContent)
    private val deleteBtn: Button = itemView.findViewById(R.id.deleteComment)


    fun bind(comment: DocumentSnapshot, i: Int) {
        if (UID2 == comment["commenterId"].toString()){
            deleteBtn.visibility = View.VISIBLE
        }
        else {
            deleteBtn.visibility = View.INVISIBLE
        }
        //Log.i("EEE", UID2)
        //Log.i("EEE", comment["commenterID"].toString())
        userTextView.text = comment["commenterName"].toString()
        contentTextView.text = comment["text"].toString()
        deleteBtn.setOnClickListener{
            comment.reference.delete().addOnSuccessListener {
            refreshWithThis.loadComments()
            }
        }
    }

}

class CommentAdapter(private val list: ArrayList<DocumentSnapshot>, private val UID: String, private val refreshWithThis1: ViewPostActivity)
    : RecyclerView.Adapter<CommentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CommentViewHolder(inflater, parent, UID, refreshWithThis1)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(list[position], position)

    }

    override fun getItemCount(): Int = list.size

}