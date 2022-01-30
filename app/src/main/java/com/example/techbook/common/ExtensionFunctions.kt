package com.example.techbook.common

import android.app.Activity
import android.widget.Toast

object ExtensionFunctions {
    //showToast
    fun Activity.showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}