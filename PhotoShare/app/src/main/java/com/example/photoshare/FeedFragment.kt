package com.example.photoshare


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.fragment_feed.*

class FeedFragment : Fragment() {

    companion object {
        var imagePost1: ImagePost = ImagePost("https://i.kym-cdn.com/entries/icons/mobile/000/026/008/Screen_Shot_2018-04-25_at_12.24.22_PM.jpg", "email.email", "YOOOOOOOOO")
        var imagePost2: ImagePost = ImagePost("https://i.kym-cdn.com/entries/icons/mobile/000/026/008/Screen_Shot_2018-04-25_at_12.24.22_PM.jpg", "SOOOO GOOOD", "SHOO BOUY")
        var imageList: ArrayList<ImagePost> = ArrayList()
    }

    val postList: ArrayList<DocumentSnapshot> = ArrayList()

    private val storage = Firebase.storage
    private val db = Firebase.firestore
    private val auth = FirebaseAuth.getInstance()

    // TODO: pull down to refresh

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        var imageadapter = ImageAdapter(postList)
        recyclerView.adapter = imageadapter
        recyclerView.layoutManager = LinearLayoutManager(this.context)

        db.collection("posts").get()
            .addOnSuccessListener {
                postList.addAll(it.documents)
                imageadapter.notifyDataSetChanged()
            }

    }

}
