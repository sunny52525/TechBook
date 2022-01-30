package com.example.techbook.domain.use_case

import com.example.techbook.data.repository.UserRepository
import com.example.techbook.domain.model.UserModel
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class CreateUserUserCase @Inject constructor(
    private val repository: UserRepository,
) {
    operator fun invoke(
        user: UserModel,
        onSuccess: () -> Unit,
        onError: (String?) -> Unit
    ) {
         repository.createUser(user, onSuccess = {
            onSuccess()
        }, onError = {
            onError(it)
        })

    }
}