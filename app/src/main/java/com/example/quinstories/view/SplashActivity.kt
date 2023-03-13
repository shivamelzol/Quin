package com.example.quinstories.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.quinstories.databinding.ActivitySplashBinding
import com.example.quinstories.utils.CommanUtils
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.lang.String
import java.util.concurrent.TimeUnit


class SplashActivity : AppCompatActivity() {

    lateinit var binding : ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)

        setContentView(binding.root)



        Handler(mainLooper).postDelayed(Runnable {
            runOnUiThread {
                callNextScreen()
            }
        }, TimeUnit.SECONDS.toMillis(2))
    }

    fun callNextScreen(){
        if (CommanUtils.isLogin(this)) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }
    }
}