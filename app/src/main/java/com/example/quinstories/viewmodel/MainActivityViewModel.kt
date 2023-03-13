package com.example.quinstories.viewmodel

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quinstories.databinding.ActivityMainBinding
import com.example.quinstories.model.Story
import com.google.firebase.database.*

class MainActivityViewModel  : ViewModel() {

    lateinit var binding: ActivityMainBinding
    private lateinit var databaseReference: DatabaseReference
    val SuccessMessage: MutableLiveData<ArrayList<Story>> = MutableLiveData()
    val failedMessage: MutableLiveData<String> = MutableLiveData()

}