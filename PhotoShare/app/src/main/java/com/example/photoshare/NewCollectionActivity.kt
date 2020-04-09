package com.example.photoshare

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_new_collection.*
import java.util.*

class NewCollectionActivity : AppCompatActivity() {
    private val db = Firebase.firestore
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_collection)

        createCollectionButton.setOnClickListener {
            if (collectionNameInput.text.isEmpty()) {
                Snackbar.make(it, "Please specify a name for your collection.", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            db.collection("privateCollections").add(hashMapOf(
                "id" to UUID.randomUUID().toString(),
                "ownerId" to auth.currentUser!!.uid,
                "name" to collectionNameInput.text.toString()
            ))
        }
    }
}
