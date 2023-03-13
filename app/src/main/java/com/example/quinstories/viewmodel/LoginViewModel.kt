package com.example.quinstories.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.database.*

class LoginViewModel() : ViewModel() {

    private lateinit var databaseReference: DatabaseReference

    fun getUserDetails() {
        val rootRef = FirebaseDatabase.getInstance().reference
        val ordersRef = rootRef.child("users").orderByChild("email").equalTo("hjjj")
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (ds in dataSnapshot.children) {
                    val username = ds.child("fullName").getValue(String::class.java)
                    println("usernan "+username)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
               // Log.d(TAG, databaseError.getMessage()) //Don't ignore errors!
            }
        }
        ordersRef.addListenerForSingleValueEvent(valueEventListener)
    }

}