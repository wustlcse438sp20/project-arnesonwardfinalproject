package com.example.photoshare.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.photoshare.R
import com.google.firebase.firestore.DocumentSnapshot


class FriendViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.friend_item, parent, false)) {


    fun bind(player: DocumentSnapshot, i: Int) {

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

    }

    override fun getItemCount(): Int = list.size

}