package com.example.techbook.domain.model

data class UiState(
    val message: String="",
    val isError: Boolean=false,
    val isLoading: Boolean=false
)
