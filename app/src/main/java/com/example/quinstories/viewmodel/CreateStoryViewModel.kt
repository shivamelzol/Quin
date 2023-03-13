package com.example.quinstories.viewmodel

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quinstories.databinding.ActivityCreateStoryBinding
import com.example.quinstories.databinding.ActivitySignUpBinding
import com.example.quinstories.model.Story
import com.example.quinstories.model.User
import com.example.quinstories.utils.Utils
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CreateStoryViewModel : ViewModel()  {

    lateinit var binding: ActivityCreateStoryBinding
    private lateinit var databaseReference: DatabaseReference
    val SuccessMessage: MutableLiveData<Story> = MutableLiveData()
    val failedMessage: MutableLiveData<String> = MutableLiveData()

    fun saveData(imageName: String) {
        if(checkValidation()) {
            binding.loading.visibility= View.VISIBLE
            databaseReference = FirebaseDatabase.getInstance().getReference("stories")
            val desc = binding.story.text.toString()

            val story = Story(desc,imageName)

            databaseReference.push().setValue(story).addOnCompleteListener {
                if (it.isSuccessful) {
                    binding.loading.visibility= View.GONE
                    //println("rerjek "+it.)
                    SuccessMessage.postValue(story)

                } else {
                    binding.loading.visibility= View.GONE
                    failedMessage.postValue("failed")
                }
            }
        }
    }

    private fun checkValidation(): Boolean {
        var ret = true
        if (!Utils.hasEditText(binding.story, "Enter Story")) ret = false
        return ret
    }
}