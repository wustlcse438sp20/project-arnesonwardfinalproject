package com.example.photoshare.Fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.photoshare.Adapters.ImageAdapter
import com.example.photoshare.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.fragment_feed.*

class FeedFragment : Fragment() {

    val postList: ArrayList<DocumentSnapshot> = ArrayList()

    private val storage = Firebase.storage
    private val db = Firebase.firestore
    private val auth = FirebaseAuth.getInstance()

    private val imageadapter = ImageAdapter(postList)

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



        accountCollectionsRecycler.adapter = imageadapter
        accountCollectionsRecycler.layoutManager = LinearLayoutManager(this.context)



    }

    private fun loadPosts() {
        postList.clear()
        db.collection("posts").get()
            .addOnSuccessListener {
                postList.addAll(it.documents)
                imageadapter.notifyDataSetChanged()
            }
    }

    override fun onResume() {
        super.onResume()
        loadPosts()
    }

}
