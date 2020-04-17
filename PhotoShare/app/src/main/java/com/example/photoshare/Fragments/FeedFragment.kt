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

    private lateinit var imageadapter: ImageAdapter

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
        imageadapter = ImageAdapter(postList, this.context!!)


        accountCollectionsRecycler.adapter = imageadapter
        accountCollectionsRecycler.layoutManager = LinearLayoutManager(this.context)

        sortMethodRadioGroup.check(R.id.mostLikedRadioButton)

        sortMethodRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            sortPosts()
        }

    }

    private fun loadPosts() {
        postList.clear()
        db.document("users/" + auth.currentUser!!.uid).get().addOnSuccessListener {
            val myFriends = it["friends"] as ArrayList<String>

            db.collection("posts").get()
                .addOnSuccessListener {
                    it.documents.forEach {
                        if (it["private"] != null && it["private"].toString().toBoolean()) {
                            val postOwner = it["owner.username"].toString()
                            if (myFriends.indexOf(postOwner) != -1 || postOwner == auth.currentUser!!.displayName) {
                                postList.add(it)
                            }
                        }
                        else {
                            postList.add(it)
                        }
                    }
                    sortPosts()
                    imageadapter.notifyDataSetChanged()
                }
        }

    }

    private fun sortPosts() {
        var method = "likes"
        if (mostDislikedRadioButton.isChecked)
            method = "dislikes"

        postList.sortBy {
            it[method]?.toString()?.toInt() ?: 0
        }

        postList.reverse()

        imageadapter.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        loadPosts()
    }

}
