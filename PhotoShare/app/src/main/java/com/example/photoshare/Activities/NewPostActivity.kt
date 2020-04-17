package com.example.photoshare.Activities

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_new_post.*
import android.content.Intent
import android.net.Uri

import android.util.Log
import android.view.View
import com.example.photoshare.R
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.ktx.storage
import com.squareup.picasso.Picasso
import java.util.*


class NewPostActivity : AppCompatActivity() {
    val PICK_IMAGE = 1

    private val storage = Firebase.storage
    private val db = Firebase.firestore
    private val auth = FirebaseAuth.getInstance()

    // Create a storage reference from our app
    private val storageRef = storage.reference

    private var imageFile: Uri? = null

    private var editingExistingPost = false
    private var imageChanged = false
    private var postDocRef: DocumentReference? = null
    private var postDocSnapshot: DocumentSnapshot? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_post)

        val editPostPath = intent.getStringExtra("editPostRefPath")

        if (editPostPath != null) {
            editingExistingPost = true
            // get image file
            // get post document
            // populate inputs (caption and imageview)

            postDocRef = db.document(editPostPath)
            postDocRef!!.get().addOnSuccessListener {
                postDocSnapshot = it
                captionInput.setText(it["caption"].toString())
                storage.getReference(it["imageName"].toString()).downloadUrl.addOnSuccessListener {
                    imageFile = it
                    Picasso.get().load(it).into(imagePreview)
                }
            }

            // change buttons
            submitPostButton.text = "Save"
            deletePostButton.visibility = View.VISIBLE

        }

        uploadImageButton.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE)
        }

        submitPostButton.setOnClickListener {
            if (captionInput.text.isEmpty() || imageFile == null) {
                Snackbar.make(it, "Please choose and image and caption", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            var imageName = ""
            if (editingExistingPost) {
                postDocRef!!.update("caption", captionInput.text.toString())
                imageName = postDocSnapshot!!["imageName"].toString()
            }
            else {
                val user = auth.currentUser!!
                imageName = user.uid + "/" + UUID.randomUUID() + ".jpg"
                addPostToFirestore(user, imageName)
            }

            if (imageChanged) {
                uploadImageToStorage(imageName)
                    .addOnSuccessListener {
                        finish()
                    }
                    .addOnFailureListener {
                        Log.i("EEE", it.localizedMessage)
                    }
            }
            else {
                finish()
            }
        }

        deletePostButton.setOnClickListener {
            postDocRef!!.delete().addOnSuccessListener {
                finish()
            }
        }

    }

    private fun addPostToFirestore(user: FirebaseUser, imageName: String): Task<DocumentReference> {
        val ownerObj = hashMapOf<String, String>(
            "id" to user.uid,
            "username" to user.displayName!!
        )

        return db.collection("posts").add(hashMapOf(
            "owner" to ownerObj,
            "caption" to captionInput.text.toString(),
            "imageName" to imageName,
            "private" to makePrivateCheckBox.isChecked
        ))
    }

    private fun uploadImageToStorage(imageName: String): UploadTask {
        val imageRef = storageRef.child(imageName)
        return imageRef.putFile(imageFile!!)
    }
    

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            return
        }

        if (requestCode == PICK_IMAGE) {
            imageChanged = true
            imageFile = data!!.data!!
            Picasso.get().load(imageFile).into(imagePreview)
//            imageRef.putFile(data!!.data!!)
        }
    }

}
