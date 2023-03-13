package com.example.quinstories.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.quinstories.model.User
import com.google.gson.Gson

object CommanUtils {

    @RequiresApi(Build.VERSION_CODES.M)
    fun isNetworkConnected(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val nw      = connectivityManager.activeNetwork ?: return false
        val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
        return when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            //for other device how are able to connect with Ethernet
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            //for check internet over Bluetooth
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
            else -> false
        }
    }


    fun isLoginUpdate(context: Context, flag: Boolean) {
        val login = context.getSharedPreferences("isLogin", Context.MODE_PRIVATE).edit()
        login.putBoolean("isLogin", flag)
        login.apply()
    }
    fun isLogin(context: Context): Boolean = context.getSharedPreferences(
        "isLogin",
        Context.MODE_PRIVATE
    ).getBoolean("isLogin", false)

    fun storeUser(context: Context, user: User) {
        val login = context.getSharedPreferences("user", Context.MODE_PRIVATE).edit()
        val gson = Gson()
        val json = gson.toJson(user)
        login.putString("user", json)
        login.apply()
    }

    fun getUser(context: Context): User{
        val gson = Gson()
        val json = context.getSharedPreferences("user", Context.MODE_PRIVATE).getString(
            "user",
            ""
        )
        val obj: User = gson.fromJson(json, User::class.java)
        return obj
    }
}