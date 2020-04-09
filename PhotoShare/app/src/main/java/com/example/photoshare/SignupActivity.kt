package com.example.photoshare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_signup.*

class SignupActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

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
            if (emailInput.text.isEmpty() || passwordInput.text.isEmpty()) {
                Snackbar.make(it, "Please enter an email and password.", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else if (confirmPasswordInput.text.toString() != passwordInput.text.toString()) {
                Snackbar.make(it, "Passwords do not match", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(emailInput.text.toString(), passwordInput.text.toString())
                .addOnSuccessListener {
                    Log.i("EEE", "signup successful")
                    startActivity(Intent(this, FeedActivity::class.java))
                }
                .addOnFailureListener {
                    Snackbar.make(v, it.localizedMessage, Snackbar.LENGTH_SHORT).show()
                }

        }
    }
}
