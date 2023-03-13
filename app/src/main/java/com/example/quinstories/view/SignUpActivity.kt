package com.example.quinstories.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.quinstories.databinding.ActivitySignUpBinding
import com.example.quinstories.utils.CommanUtils
import com.example.quinstories.utils.Utils
import com.example.quinstories.viewmodel.SignUpViewModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {

    private lateinit var viewModel: SignUpViewModel
    private lateinit var binding: ActivitySignUpBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[SignUpViewModel::class.java]
        viewModel.binding = binding
        binding.setViewModel(viewModel)
        binding.setLifecycleOwner(this)

        viewModel.SignUpSuccessMessage.observe(this, Observer {
            CommanUtils.isLoginUpdate(applicationContext, true)
            CommanUtils.storeUser(this, it)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finishAffinity()
        })
        viewModel.failedMessage.observe(this) { it ->
            it?.let {
                Toast.makeText(this, "Please try after some time", Toast.LENGTH_SHORT).show()
            }
        }

        binding.SignUp.setOnClickListener {

            viewModel.saveData()

        }

    }
}