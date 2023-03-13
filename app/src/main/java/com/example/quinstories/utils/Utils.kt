package com.example.quinstories.utils

import android.widget.EditText
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import java.util.regex.Matcher
import java.util.regex.Pattern

class Utils {

    companion object {
        fun hasEditText(editText: EditText, errMsg: String??
        ): Boolean {
            if (editText.text.toString().trim { it <= ' ' }.isEmpty()) {
                editText.error = errMsg
                editText.requestFocus()//(editText, context)
                return false
            } else {
                editText.isFocusable= false
            }
            return true
        }

        fun emailValid(editText: EditText,errMsg: String): Boolean{
            if(isEmailValid(editText.text.toString())){
               // editText.isFocusable= false
                return true
            }else{
                editText.error = errMsg
                editText.requestFocus()
                return false
            }
        }

        fun isEmailValid(email: String?): Boolean {
            val pattern: Pattern
            val matcher: Matcher
            val EMAIL_PATTERN =
                "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$"
            pattern = Pattern.compile(EMAIL_PATTERN)
            matcher = pattern.matcher(email)
            return matcher.matches()
        }
    }

}