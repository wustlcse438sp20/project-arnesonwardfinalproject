package com.example.photoshare.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.photoshare.Adapters.FriendAdapter
import com.example.photoshare.Adapters.RequestAdapter
import com.example.photoshare.R
import kotlinx.android.synthetic.main.activity_friends.*

class FriendsActivity : AppCompatActivity() {


    companion object{
        val friendList: ArrayList<String> = ArrayList()
        val requestList:ArrayList<String> = ArrayList()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends)
        for (i in 1..8) {
            friendList.add("")
            requestList.add("")
        }
        val friendadapter = FriendAdapter(friendList)
        friendsRecyclerView.adapter = friendadapter
        friendsRecyclerView.layoutManager = LinearLayoutManager(this)
        val requestadapter = RequestAdapter(requestList)
        requestRecyclerView.adapter = requestadapter
        requestRecyclerView.layoutManager = LinearLayoutManager(this)
    }

}