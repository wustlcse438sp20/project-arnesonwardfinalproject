package com.example.photoshare.Activities

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.photoshare.Adapters.CommentAdapter
import com.example.photoshare.R
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_view_post.*

class ViewPostActivity : AppCompatActivity() {


    private val commentList: ArrayList<DocumentSnapshot> = ArrayList()

    private val db = Firebase.firestore
    private val storage = Firebase.storage
    private val auth = FirebaseAuth.getInstance()
    private val commentAdapter = CommentAdapter(commentList, auth.currentUser!!.uid, this)

    private lateinit var postId: String
    private lateinit var postDocRef: DocumentReference
    private lateinit var postDocumentSnapshot: DocumentSnapshot

    private var likes = 0
    private var dislikes = 0

    private var liked = false
    private var disliked = false

    private var myVote: DocumentReference? = null

    private val LIKED_COLOR = Color.GREEN
    private val DISLIKED_COLOR = Color.RED

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_post)


        postId = intent.getStringExtra("postId")!!

        postDocRef = db.document("posts/$postId")
        postDocRef.get().addOnSuccessListener {
            var captionText = it["caption"].toString()
            if (it["private"] != null && it["private"].toString().toBoolean()) {
                captionText += "\n(PRIVATE)"
            }

            username.text = it["owner.username"].toString()
            caption.text = captionText
            storage.getReference(it["imageName"].toString()).downloadUrl.addOnSuccessListener {
                Picasso.get().load(it).into(imagePost)
            }
        }

        commentsRecycler.adapter = commentAdapter
        commentsRecycler.layoutManager = LinearLayoutManager(this)

        loadComments()
        loadVotes()

        commentInput.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
                submitCommentButton.isEnabled = !(after == 0 && start == 0 && count == 1 && commentInput.text.length == 1)
            }
            override fun afterTextChanged(s: Editable) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        submitCommentButton.setOnClickListener {
            db.collection("comments").add(hashMapOf(
                "postId" to postId,
                "text" to commentInput.text.toString(),
                "commenterId" to auth.currentUser!!.uid,
                "commenterName" to auth.currentUser!!.displayName
            )).addOnSuccessListener {
                loadComments()
            }
            commentInput.text.clear()
        }

        likeButton.drawable.setTint(Color.BLACK)
        dislikeButton.drawable.setTint(Color.BLACK)
        likeButton.setOnClickListener {
            liked = !liked
            if (liked) likes++ else likes--

            if (disliked) dislikes--
            disliked = false
            handleVoteButtonClick()
        }
        dislikeButton.setOnClickListener {
            disliked = !disliked
            if (disliked) dislikes++ else dislikes--

            if (liked) likes--
            liked = false
            handleVoteButtonClick()
        }

    }

    private fun handleVoteButtonClick() {
        updateVotesView()
        castVote(liked, disliked).addOnSuccessListener {
            loadVotes()
        }
    }

    private fun loadVotes() {
        likes = 0
        dislikes = 0
        db.collection("votes")
            .whereEqualTo("postId", postId)
            .get()
            .addOnSuccessListener {
                it.documents.forEach {
                    if (it["up"].toString().toBoolean()) {
                        likes++
                    }
                    else if (it["down"].toString().toBoolean()) {
                        dislikes++
                    }

                    if (it["voterId"].toString() == auth.currentUser!!.uid) {
                        myVote = it.reference
                        liked = it["up"].toString().toBoolean()
                        disliked = it["down"].toString().toBoolean()
                    }
                }
                updateVotesView()
                setPostVoteCounts()
            }
    }

    private fun setPostVoteCounts() {
        postDocRef.update(hashMapOf(
            "dislikes" to dislikes,
            "likes" to likes
        ) as Map<String, Any>)
    }

    private fun castVote(up: Boolean, down: Boolean): Task<Any> {
        if (myVote == null) {
            return db.collection("votes").add(hashMapOf(
                "postId" to postId,
                "voterId" to auth.currentUser!!.uid,
                "up" to up,
                "down" to down
            )).addOnSuccessListener {
                myVote = it
            } as Task<Any>
        }
        else {
            return myVote!!.update(hashMapOf(
                "up" to up,
                "down" to down
            ) as Map<String, Any>) as Task<Any>
        }
    }

    private fun updateVotesView() {
        // update view of likes/dislikes nums
        likeButton.drawable.setTint(Color.BLACK)
        dislikeButton.drawable.setTint(Color.BLACK)
        if (liked) {
            likeButton.drawable.setTint(LIKED_COLOR)
        }
        if (disliked) {
            dislikeButton.drawable.setTint(DISLIKED_COLOR)
        }

        likesTextView.text = likes.toString()
        dislikesTextView.text = dislikes.toString()
    }

    fun loadComments() {
        commentList.clear()
        db.collection("comments")
            .whereEqualTo("postId", postId)
            .get()
            .addOnSuccessListener {
                commentList.addAll(it.documents)
                commentAdapter.notifyDataSetChanged()
            }

    }


}
