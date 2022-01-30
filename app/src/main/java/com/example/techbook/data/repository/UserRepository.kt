package com.example.techbook.data.repository

import android.util.Log
import com.example.techbook.common.Resource
import com.example.techbook.domain.model.UserModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

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


    fun getUser() = flow<Resource<UserModel?>> {
        val firebaseUser = Firebase.auth.currentUser
        firebaseUser?.let {
            emit(Resource.Loading())
            try {
                val user = db.collection("users").document(firebaseUser.uid).get().await()
                Log.d(TAG, "getUser:${user.data} ")
                val userModel = user.toObject(UserModel::class.java)
                userModel?.let {
                    emit(Resource.Success(userModel))
                }
            } catch (e: Exception) {
                emit(Resource.Error(e.message.toString(), null))
            }

        }
    }

    companion object {
        private const val TAG = "UserRepository"
    }
}