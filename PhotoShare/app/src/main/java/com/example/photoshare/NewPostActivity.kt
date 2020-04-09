package com.example.photoshare

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_new_post.*
import android.content.Intent

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.util.*


class NewPostActivity : AppCompatActivity() {
    val PICK_IMAGE = 1

    private val storage = Firebase.storage
    private val db = Firebase.firestore
    private val auth = FirebaseAuth.getInstance()

    // Create a storage reference from our app
    private val storageRef = storage.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_post)

        uploadImageButton.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            return
        }

        val user = auth.currentUser!!

        val imageName = user.uid + "/" + UUID.randomUUID() + ".jpg"

        val ownerObj = hashMapOf<String, String>(
            "id" to user.uid,
            "username" to user.email!!
        )

        db.collection("posts").add(hashMapOf(
            "owner" to ownerObj,
            "caption" to "fakeCaption",
            "imageName" to imageName
        ))

        // Create a reference to "mountains.jpg"
        val imageRef = storageRef.child(imageName)

        if (requestCode == PICK_IMAGE) {
            Log.i("EEE", data.toString())
            imageRef.putFile(data!!.data!!)
        }
    }

}
