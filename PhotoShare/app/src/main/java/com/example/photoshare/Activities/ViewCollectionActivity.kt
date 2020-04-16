package com.example.photoshare.Activities

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.photoshare.Adapters.CollectionViewAdapter
import com.example.photoshare.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_view_collection.*
import java.util.*
import kotlin.collections.ArrayList

class ViewCollectionActivity : AppCompatActivity() {
    private val storage = Firebase.storage

    private val uriList: ArrayList<Uri> = ArrayList()
    private val pathList: ArrayList<String> = ArrayList()
    private val collectionViewAdapter = CollectionViewAdapter(uriList, pathList, storage, this)


    private lateinit var privateCollectionId: String
    private lateinit var privateCollectionName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_collection)

        privateCollectionId = intent.getStringExtra("privateCollectionId")!!
        privateCollectionName = intent.getStringExtra("privateCollectionName")!!

        collectionNameTextView.text = privateCollectionName


        accountCollectionsRecycler.adapter = collectionViewAdapter
        accountCollectionsRecycler.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)//LinearLayoutManager(this)

        addImageButton.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1)
        }

        loadCollectionImages()

    }

    fun loadCollectionImages() {
        uriList.clear()
        pathList.clear()
        val collectionRef = storage.getReference("privateCollections/$privateCollectionId")
        collectionRef.listAll()
            .addOnSuccessListener {
                it.items.forEach {
                    val path = it.path
//                    pathList.add(path)
                    it.downloadUrl.addOnSuccessListener {
                        uriList.add(it)
                        pathList.add(path)
                        collectionViewAdapter.notifyDataSetChanged()
                    }
//                    collectionItemList.add(it.)
                }
//                collectionViewAdapter.notifyDataSetChanged()
                Log.i("EEE", it.toString())
            }
            .addOnFailureListener {
//                collectionViewAdapter.notifyDataSetChanged()
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            return
        }

        if (requestCode == 1) {
            val imageName = UUID.randomUUID().toString()
            val imageRef = storage.getReference("privateCollections/$privateCollectionId/$imageName.jpg")
            imageRef.putFile(data!!.data!!).addOnSuccessListener {
                loadCollectionImages()
            }
        }
    }
}
