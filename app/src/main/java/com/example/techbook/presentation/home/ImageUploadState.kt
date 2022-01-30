package com.example.techbook.presentation.home

data class ImageUploadState(
    val isLoading: Boolean=false,
    val isSuccess: Boolean=false,
    val isError: Boolean=false,
    val errorMessage: String?=null,
    val imageUrl: String?=null
)
