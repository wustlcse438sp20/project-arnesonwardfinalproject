package com.example.photoshare.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.photoshare.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_signup.*

class SignupActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        goToLoginButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        signupButton.setOnClickListener {
            val v = it
            if (emailInput.text.isEmpty() || passwordInput.text.isEmpty() || displayNameInput.text.isEmpty()) {
                Snackbar.make(it, "Please enter an email, username and password.", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else if (confirmPasswordInput.text.toString() != passwordInput.text.toString()) {
                Snackbar.make(it, "Passwords do not match", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val displayName = displayNameInput.text.toString()

            // make sure username isn't taken
            db.collection("users")
                .whereEqualTo("displayName", displayName)
                .get()
                .addOnSuccessListener {
                    if (it.documents.isNotEmpty()) {
                        Snackbar.make(v, "Username taken", Snackbar.LENGTH_LONG).show()
                        return@addOnSuccessListener
                    }

                    auth.createUserWithEmailAndPassword(emailInput.text.toString(), passwordInput.text.toString())
                        .addOnSuccessListener {
                            Log.i("EEE", "signup successful")
                            val builder = UserProfileChangeRequest.Builder()
                            builder.setDisplayName(displayName)
                            auth.currentUser!!.updateProfile(builder.build())
                                .addOnSuccessListener {
                                    db.document("users/" + auth.currentUser!!.uid).set(hashMapOf(
                                        "id" to auth.currentUser!!.uid,
                                        "displayName" to displayName,
                                        "friends" to ArrayList<String>()
                                    )).addOnSuccessListener {
                                        startActivity(Intent(this, FeedActivity::class.java))
                                    }
                                }
                        }
                        .addOnFailureListener {
                            Snackbar.make(v, it.localizedMessage, Snackbar.LENGTH_SHORT).show()
                        }

                }



        }
    }
}
