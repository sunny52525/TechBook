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

    private val allBadges = mutableStateOf<Resource<List<Badge>>>(Resource.Success(emptyList()))
    val badges: State<Resource<List<Badge>>> = allBadges

    private val allBadgeListFromCollege =
        mutableStateOf<Resource<List<Badge>>>(Resource.Success(emptyList()))
    val badgeListFromCollege: State<Resource<List<Badge>>> = allBadgeListFromCollege

    var uiState = mutableStateOf(UiState())
        private set

    val imageList = mutableStateOf(listOf<String?>())

    init {
        getUser()
        getAllBadges()
    }

    private fun getUser() {

        userRepository.getUser().onEach {
            _user.value = it
            if (it is Resource.Success) {
                getBadgesFromCollege(it.data?.college)
            } }.launchIn(viewModelScope)
    }

    private fun getBadgesFromCollege(college: String?) {
        userRepository.getBadgesFromCollege(college).onEach {
            allBadgeListFromCollege.value = it
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
        userRepository.addBadge(
            badge.copy(
                name = user.value.data?.name.toString(),
                collegeName = user.value.data?.college.toString(),

                )
        )
            .onEach { addBadgeResult ->
                if (addBadgeResult is Resource.Success) {
                    imageList.value = emptyList()
                    getUser()
                    getAllBadges()

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

    private fun getAllBadges() {
        userRepository.getAllBadges().onEach {
            allBadges.value = it
        }.launchIn(viewModelScope)
    }

    fun searchBadgesByCollege(collegeName: String) {

        userRepository.getBadgesFromCollege(collegeName).onEach {
            allBadgeListFromCollege.value = it
        }.launchIn(viewModelScope)
    }


}


