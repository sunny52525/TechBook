package com.example.techbook.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.techbook.common.Resource
import com.example.techbook.data.repository.UserRepository
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


    init {
        getUser()
    }

    private fun getUser() {

        userRepository.getUser().onEach {
            _user.value = it
        }.launchIn(viewModelScope)
    }

}