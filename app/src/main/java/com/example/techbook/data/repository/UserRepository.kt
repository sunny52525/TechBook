package com.example.techbook.data.repository

import android.graphics.Bitmap
import android.util.Log
import com.example.techbook.common.Resource
import com.example.techbook.domain.model.Badge
import com.example.techbook.domain.model.UserModel
import com.example.techbook.presentation.home.ImageUploadState
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream

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

    fun uploadImage(bitmap: Bitmap) = flow<ImageUploadState> {
        emit(ImageUploadState(isLoading = true))
        try {
            val firebaseUser = Firebase.auth.currentUser
            val storageRef = Firebase.storage.reference
            val imageRef =
                storageRef.child("images/${firebaseUser?.uid}/${System.currentTimeMillis()}")


            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val data = baos.toByteArray()
            imageRef.putBytes(data).await()

            emit(
                ImageUploadState(
                    isLoading = false,
                    isSuccess = true,
                    imageUrl = imageRef.downloadUrl.await().toString()
                )
            )
        } catch (e: Exception) {
            emit(ImageUploadState(isLoading = false, isSuccess = false, errorMessage = e.message))
        }

    }

    fun addBadge(badge: Badge) = flow<Resource<Boolean>> {
        emit(Resource.Loading())
        try {
            Firebase.auth.currentUser?.let { firebaseUser ->
                val badgeRef = db.collection("users").document(firebaseUser.uid)
                badgeRef.update("badges", FieldValue.arrayUnion(badge.toMap())).await()
                emit(Resource.Success(true))
            }

        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString(), false))
        }
    }


    companion object {
        private const val TAG = "UserRepository"
    }
}