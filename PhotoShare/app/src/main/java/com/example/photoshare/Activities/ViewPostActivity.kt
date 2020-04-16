package com.example.photoshare.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.photoshare.Adapters.CommentAdapter
import com.example.photoshare.R
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.android.synthetic.main.activity_view_post.*

class ViewPostActivity : AppCompatActivity() {


    companion object {
        val commentList: ArrayList<String> = ArrayList()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_post)
        //commentList.add("EEEs nuts")
        commentList.add("real comment")
        commentList.add("")
        commentList.add("")
        commentList.add("")
        var commentadapter = CommentAdapter(commentList)
        recyclerView.adapter = commentadapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }


}
