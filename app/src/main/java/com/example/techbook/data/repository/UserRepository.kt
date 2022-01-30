package com.example.techbook.data.repository

import android.util.Log
import com.example.techbook.domain.model.UserModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class UserRepository(
    private val db: FirebaseFirestore,

    ) {

    fun createUser(user: UserModel, onSuccess: () -> Unit, onError: (String?) -> Unit) {
        val firebaseUser = Firebase.auth.currentUser
        firebaseUser?.let {
            val userHashMap = user.copy(id = firebaseUser.uid).toHashMap()
            db.collection("users").document(firebaseUser.uid).set(userHashMap)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        onSuccess()

                        Log.d("TAG", "createUser:SUCCESS")
                    } else {
                        onError(it.exception?.message)
                        Log.d("TAG", "createUser:FAILURE")

                    }
                }
                .addOnFailureListener {
                    onError(it.message)
                    Log.d("TAG", "createUser:FAILURE")


                }
        }

    }

}