package com.example.photoshare.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.photoshare.Adapters.FriendAdapter
import com.example.photoshare.Adapters.RequestAdapter
import com.example.photoshare.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_friends.*

class FriendsActivity : AppCompatActivity() {

    private val db = Firebase.firestore
    private val auth = FirebaseAuth.getInstance()

    private val friendList: ArrayList<String> = ArrayList()
    private val requestList: ArrayList<DocumentSnapshot> = ArrayList()

    private lateinit var friendAdapter: FriendAdapter
    private lateinit var requestAdapter: RequestAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends)

        loadFriendRequests()
        loadFriends()




        addFriend.setOnClickListener {
            val v = it
            if (searchForFriends.text.isEmpty()) return@setOnClickListener

            val friendName = searchForFriends.text.toString()

            if (friendName == auth.currentUser!!.displayName) {
                Snackbar.make(v, "That's you!", Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
            }

            db.collection("users")
                .whereEqualTo("displayName", friendName)
                .get()
                .addOnSuccessListener {
                    if (it.documents.isEmpty()) {
                        Snackbar.make(v, "No user with that name", Snackbar.LENGTH_LONG).show()
                        return@addOnSuccessListener
                    }
                    val bob = it.documents[0]

                    val bobsFriends = bob["friends"] as ArrayList<String>
                    if (bobsFriends.indexOf(auth.currentUser!!.uid) != -1) {
                        Snackbar.make(v, "You are already friends with that person", Snackbar.LENGTH_LONG).show()
                        return@addOnSuccessListener
                    }

                    val friendId = bob["id"]

                    db.collection("friendRequests")
                        .whereEqualTo("fromId", auth.currentUser!!.uid)
                        .whereEqualTo("toId", friendId)
                        .get()
                        .addOnSuccessListener {
                            if (it.documents.isNotEmpty()) {
                                Snackbar.make(v, "You already requested this person", Snackbar.LENGTH_LONG).show()
                                return@addOnSuccessListener
                            }
                            db.collection("friendRequests").add(hashMapOf(
                                "fromId" to auth.currentUser!!.uid,
                                "fromName" to auth.currentUser!!.displayName,
                                "toId" to friendId
                            )).addOnSuccessListener {
                                Snackbar.make(v, "Sent friend request to $friendName", Snackbar.LENGTH_LONG).show()
                                searchForFriends.text.clear()
                            }
                        }



                }
        }

        friendAdapter = FriendAdapter(friendList)
        friendsRecyclerView.adapter = friendAdapter
        friendsRecyclerView.layoutManager = LinearLayoutManager(this)

        requestAdapter = RequestAdapter(requestList, this)
        requestRecyclerView.adapter = requestAdapter
        requestRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun loadFriends() {
        friendList.clear()
        db.document("users/" + auth.currentUser!!.uid).get().addOnSuccessListener {
            friendList.addAll(it["friends"] as Collection<String>)
            friendAdapter.notifyDataSetChanged()
        }
    }


    private fun loadFriendRequests() {
        requestList.clear()
        db.collection("friendRequests")
            .whereEqualTo("toId", auth.currentUser!!.uid)
            .get()
            .addOnSuccessListener {
                requestList.addAll(it.documents)
                requestAdapter.notifyDataSetChanged()
            }
    }

    fun answerRequest(reqSnapshot: DocumentSnapshot, accept: Boolean) {
        if (accept) {
            // add to both friend arrays
            db.document("users/" + reqSnapshot["fromId"])
                .get()
                .addOnSuccessListener {
                    val friendsOfFriend = it["friends"] as ArrayList<String>
                    val friendName = it["displayName"].toString()
                    val friendId = it["id"].toString()

                    friendsOfFriend.add(auth.currentUser!!.displayName!!)
//                    friendsOfFriend.add(auth.currentUser!!.uid)
                    it.reference.update("friends", friendsOfFriend)

                    db.document("users/" + auth.currentUser!!.uid).get().addOnSuccessListener {
                        val myFriends = it["friends"] as ArrayList<String>

                        myFriends.add(friendName)
                        it.reference.update("friends", myFriends).addOnSuccessListener {
                            // load friends
                            loadFriends()
                        }
                    }
                }

            reqSnapshot.reference.delete().addOnSuccessListener {
                // update requests view
                loadFriendRequests()
            }



//            db.document("friends/" + auth.currentUser!!.uid).get()
//                .addOnSuccessListener {
//                    Log.i("EEE", it.toString())
//                }
//                .addOnFailureListener {
//                    Log.i("EEE", it.toString())
//                }
        }
        else {
            reqSnapshot.reference.delete().addOnSuccessListener {
                loadFriendRequests()
            }
        }
    }

}