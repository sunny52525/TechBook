package com.example.techbook.domain.use_case

import androidx.compose.runtime.MutableState
import com.example.techbook.data.repository.UserRepository
import com.example.techbook.domain.model.UserModel
import javax.inject.Inject

class CreateUserUserCase @Inject constructor(
    private val repository: UserRepository,
) {
    operator fun invoke(
        user: UserModel,
        onSuccess: () -> Unit,
        onError: (String?) -> Unit,
        referral: String
    ) {
         repository.createUser(user, onSuccess = {
            onSuccess()
        }, onError = {
            onError(it)
        },referral = referral)

    }
}