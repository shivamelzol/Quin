package com.example.quinstories.viewmodel

import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quinstories.databinding.ActivitySignUpBinding
import com.example.quinstories.model.User
import com.example.quinstories.utils.Utils
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpViewModel  : ViewModel()  {

    lateinit var binding: ActivitySignUpBinding
    private lateinit var databaseReference: DatabaseReference
    val SignUpSuccessMessage: MutableLiveData<User> = MutableLiveData()
    val failedMessage: MutableLiveData<String> = MutableLiveData()

    fun saveData() {
        if(checkValidation()) {
            binding.loading.visibility=View.VISIBLE
            databaseReference = FirebaseDatabase.getInstance().getReference("users")
            val fullname = binding.name.text.toString()
            val email = binding.email.text.toString()
            val pass = binding.password.text.toString()

            val user = User(fullname, email, pass)

            databaseReference.push().setValue(user).addOnCompleteListener {
                if (it.isSuccessful) {
                    binding.loading.visibility=View.GONE
                    //println("rerjek "+it.)
                    SignUpSuccessMessage.postValue(user)

                } else {
                    binding.loading.visibility=View.GONE
                    failedMessage.postValue("failed")
                }
            }
        }
    }

    private fun checkValidation(): Boolean {
        var ret = true
        if(!Utils.emailValid(binding.email,"Enter valid email")) ret=false
        if (!Utils.hasEditText(binding.password, "Enter Password")) ret = false
       // if (!Utils.hasEditText(binding.email, "Enter Email")) ret = false
        if (!Utils.hasEditText(binding.name, "Enter Full Name")) ret = false

        return ret
    }
}