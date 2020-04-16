package com.example.photoshare.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.photoshare.R
import com.google.firebase.firestore.DocumentSnapshot

class RequestViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.request_item, parent, false)) {


    fun bind(player: DocumentSnapshot, i: Int) {

    }

}

class RequestAdapter(private val list: ArrayList<String>)
    : RecyclerView.Adapter<RequestViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return RequestViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: RequestViewHolder, position: Int) {
        //val : DocumentSnapshot = list[position]

    }

    override fun getItemCount(): Int = list.size

}