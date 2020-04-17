package com.example.photoshare.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.photoshare.Activities.FriendsActivity
import com.example.photoshare.R
import com.google.firebase.firestore.DocumentSnapshot

class RequestViewHolder(inflater: LayoutInflater, parent: ViewGroup, private val friendsActivity: FriendsActivity) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.request_item, parent, false)) {

    private val reqUNameText: TextView = itemView.findViewById(R.id.requestUsername)
    private val acceptBtn: Button = itemView.findViewById(R.id.acceptRequest)
    private val rejectBtn: Button = itemView.findViewById(R.id.declineRequest)


    fun bind(req: DocumentSnapshot, i: Int) {
        reqUNameText.text = req["fromName"].toString()

        acceptBtn.isEnabled = true
        rejectBtn.isEnabled = true

        acceptBtn.setOnClickListener {
            acceptBtn.isEnabled = false
            rejectBtn.isEnabled = false
            friendsActivity.answerRequest(req, true)
        }
        rejectBtn.setOnClickListener {
            acceptBtn.isEnabled = false
            rejectBtn.isEnabled = false
            friendsActivity.answerRequest(req, false)
        }
    }

}

class RequestAdapter(private val list: ArrayList<DocumentSnapshot>, private val friendsActivity: FriendsActivity)
    : RecyclerView.Adapter<RequestViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return RequestViewHolder(inflater, parent, friendsActivity)
    }

    override fun onBindViewHolder(holder: RequestViewHolder, position: Int) {
        //val : DocumentSnapshot = list[position]
        holder.bind(list[position], position)
    }

    override fun getItemCount(): Int = list.size

}