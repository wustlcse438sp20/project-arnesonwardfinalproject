package com.example.photoshare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        goToSignupButton.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }

        loginButton.setOnClickListener {
            val v = it
            if (emailInput.text.isEmpty() || passwordInput.text.isEmpty()) {
                Snackbar.make(it, "Please enter an email and password.", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(emailInput.text.toString(), passwordInput.text.toString())
                .addOnSuccessListener {
                    Log.i("EEE", "login success")
                    startActivity(Intent(this, FeedActivity::class.java))
                }
                .addOnFailureListener {
                    Snackbar.make(v, it.localizedMessage, Snackbar.LENGTH_SHORT).show()
                }
        }
    }
}
