package com.example.techbook.presentation.home

import android.graphics.Bitmap
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.techbook.common.Resource
import com.example.techbook.data.repository.UserRepository
import com.example.techbook.domain.model.Badge
import com.example.techbook.domain.model.UiState
import com.example.techbook.domain.model.UserModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {


    private val _user = mutableStateOf<Resource<UserModel?>>(Resource.Success(null))
    val user: State<Resource<UserModel?>> = _user


    var uiState = mutableStateOf(UiState())
        private set

    val imageList = mutableStateOf(listOf<String?>())

    init {
        getUser()
    }

    private fun getUser() {

        userRepository.getUser().onEach {
            _user.value = it
        }.launchIn(viewModelScope)
    }

    fun uploadImage(bitmap: Bitmap) {
        userRepository.uploadImage(bitmap).onEach {

            if (it.isError) {
                setIsError(it.errorMessage.toString())
            }
            if (it.isSuccess) {
                val images = imageList.value + it.imageUrl
                imageList.value = emptyList()
                imageList.value = images
                setLoading(false)
            }
            if (it.isLoading) {
                setLoading()
            }

        }.launchIn(viewModelScope)
    }

    fun setIsError(message: String = "", isError: Boolean = true) {
        this.uiState.value = UiState(message = message, isError = isError, isLoading = false)
    }

    fun setLoading(isLoading: Boolean = true) {
        this.uiState.value = UiState(message = "", isError = false, isLoading = isLoading)
    }

    fun addBadge(badge: Badge, onSuccess: () -> Unit) {
        userRepository.addBadge(badge).onEach { addBadgeResult ->
            if (addBadgeResult is Resource.Success) {
                imageList.value = emptyList()
                getUser()

                onSuccess()
                setLoading(false)
            }
            if (addBadgeResult is Resource.Error) {
                setIsError(addBadgeResult.message.toString())
            }
            if (addBadgeResult is Resource.Loading) {
                setLoading()
            }
        }.launchIn(viewModelScope)
    }
}


