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

    fun createUser(
        user: UserModel,
        onSuccess: () -> Unit,
        onError: (String?) -> Unit,
        referral: String
    ) {
        val firebaseUser = Firebase.auth.currentUser
        firebaseUser?.let {
            val userHashMap = user.copy(id = firebaseUser.uid, referPoint = "10").toHashMap()
            db.collection("users").document(firebaseUser.uid).set(userHashMap)
                .addOnCompleteListener {
                    if (it.isSuccessful) {


                        db.collection("users").document(referral).get()
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    val referPoint =
                                        (task.result?.data?.get("referPoint") as String).toIntOrNull()
                                            ?: 0
                                    db.collection("users").document(referral)
                                        .update("referPoint", "${referPoint + 10}")
                                }
                            }

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

    fun uploadImage(bitmap: Bitmap) = flow {
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

    fun addBadge(badge: Badge) = flow {
        emit(Resource.Loading())
        try {
            Firebase.auth.currentUser?.let { firebaseUser ->
                val badgeRef = db.collection("users").document(firebaseUser.uid)
                badgeRef.update("badges", FieldValue.arrayUnion(badge.toMap())).await()

                val allBadge = db.collection("badges").document(badge.collegeName ?: "Unnamed")

                try {
                    allBadge.update("badges", FieldValue.arrayUnion(badge.toMap())).await()

                } catch (e: Exception) {
                    allBadge.set(mapOf("badges" to badge.toMap())).await()

                }
                emit(Resource.Success(true))
            }

        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString(), false))
        }
    }

    fun getAllBadges() = flow<Resource<List<Badge>>> {

        emit(Resource.Loading())
        try {
            Firebase.auth.currentUser?.uid?.let { uid ->

                val allBadges = (db.collection("users").document(uid).get()
                    .await().data?.get("badges") as? List<Map<String, Any>>)?.map {
                    Badge.fromMap(it)
                }

                Log.d(TAG, "getAllBadges: $allBadges")
                emit(Resource.Success(allBadges ?: emptyList()))
            }

        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }

    fun getBadgesFromCollege(college: String?) = flow<Resource<List<Badge>>> {
        emit(Resource.Loading())
        try {


            val ref = (db.collection("badges").document(college ?: "Unnamed").get()
                .await().data)
            try {
                val res = (ref?.get("badges") as? List<Map<String, Any>>?)
                    ?: throw Exception("No Badges Found")
                val result = res?.map {
                    Badge.fromMap(it)
                }
                emit(Resource.Success(result ?: emptyList()))
            } catch (e: Exception) {
                val res = (ref?.get("badges") as? Map<String, Any>?)
                val result =
                    Badge.fromMap(res ?: emptyMap())

                emit(Resource.Success(listOf(result) ?: emptyList()))
            }


        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }


    companion object {
        private const val TAG = "UserRepository"
    }
}