package com.example.photoshare.Fragments


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.photoshare.Adapters.AccountCollectionAdapter
import com.example.photoshare.Activities.NewCollectionActivity
import com.example.photoshare.Activities.NewPostActivity
import com.example.photoshare.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_account.*
import kotlinx.android.synthetic.main.fragment_account.recyclerView


class AccountFragment : Fragment() {

    private val privateCollections: ArrayList<DocumentSnapshot> = ArrayList()

    private val db = Firebase.firestore
    private val auth = FirebaseAuth.getInstance()
    private lateinit var collectionadapter: AccountCollectionAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        collectionadapter =
            AccountCollectionAdapter(privateCollections, context!!)
        recyclerView.adapter = collectionadapter
        recyclerView.layoutManager = LinearLayoutManager(this.context)

        newPostButton.setOnClickListener {
            startActivity(Intent(context, NewPostActivity::class.java))
        }
        newCollectionButton.setOnClickListener {
            startActivity(Intent(context, NewCollectionActivity::class.java))
        }

//        loadPrivateCollections()

    }

    private fun loadPrivateCollections() {
        privateCollections.clear()
        db.collection("privateCollections")
            .whereEqualTo("ownerId", auth.currentUser!!.uid)
            .get()
            .addOnSuccessListener {
                privateCollections.addAll(it.documents)
                collectionadapter.notifyDataSetChanged()
            }
    }

    override fun onResume() {
        super.onResume()
        loadPrivateCollections()
    }




}
