package com.example.photoshare.Activities

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

//    companion object{
//        val collectionItemList: ArrayList<String> = ArrayList()
//        val url1 = "https://i.kym-cdn.com/entries/icons/mobile/000/026/008/Screen_Shot_2018-04-25_at_12.24.22_PM.jpg"
//        val url2 = "https://i.kym-cdn.com/entries/icons/mobile/000/026/008/Screen_Shot_2018-04-25_at_12.24.22_PM.jpg"
//    }

    private val collectionItemList: ArrayList<Uri> = ArrayList()
    private val collectionViewAdapter =
        CollectionViewAdapter(collectionItemList)

    private val storage = Firebase.storage
    private val db = Firebase.firestore
    private val auth = FirebaseAuth.getInstance()

    private lateinit var privateCollectionId: String
    private lateinit var privateCollectionName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_collection)

        privateCollectionId = intent.getStringExtra("privateCollectionId")!!
        privateCollectionName = intent.getStringExtra("privateCollectionName")!!

        collectionNameTextView.text = privateCollectionName


        recyclerView.adapter = collectionViewAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        addImageButton.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1)
        }

        loadCollectionImages()

    }

    private fun loadCollectionImages() {
        collectionItemList.clear()
        val collectionRef = storage.getReference("privateCollections/$privateCollectionId")
        collectionRef.listAll()
            .addOnSuccessListener {
                it.items.forEach {
                    it.downloadUrl.addOnSuccessListener {
                        collectionItemList.add(it)
                        collectionViewAdapter.notifyDataSetChanged()
                    }
//                    collectionItemList.add(it.)
                }
                Log.i("EEE", it.toString())
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
