package com.example.techbook.domain.model

data class Badge(
    val typeOfBadge: String,
    val moreInfo: String,
    val imageUrls: List<String?>,
    val name: String = ""
) {
    fun toMap(): Map<String, Any> {
        return mapOf(
            "typeOfBadge" to typeOfBadge,
            "moreInfo" to moreInfo,
            "imageUrls" to imageUrls,
            "name" to name
        )
    }
}

