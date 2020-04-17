package com.example.photoshare.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.photoshare.R
import com.google.firebase.firestore.DocumentSnapshot


class FriendViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.friend_item, parent, false)) {
    val friendUsername: TextView = itemView.findViewById(R.id.friendUsername)

    fun bind(friendName: String, i: Int) {
        friendUsername.text = friendName
    }

}

class FriendAdapter(private val list: ArrayList<String>)
    : RecyclerView.Adapter<FriendViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return FriendViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        //val : DocumentSnapshot = list[position]
        holder.bind(list[position], position)
    }

    override fun getItemCount(): Int = list.size

}