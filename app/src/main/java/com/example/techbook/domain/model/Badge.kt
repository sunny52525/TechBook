package com.example.techbook.domain.model

import com.example.techbook.common.Utils.getDateAndTime

data class Badge(
    val typeOfBadge: String="",
    val moreInfo: String="",
    val imageUrls: List<String?> = emptyList(),
    val name: String = "",
    val collegeName:String? = null,
    val time: String? = getDateAndTime(),
) {
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "typeOfBadge" to typeOfBadge,
            "moreInfo" to moreInfo,
            "imageUrls" to imageUrls,
            "name" to name,
            "collegeName" to collegeName,
            "time" to time
        )
    }
    companion object{
        fun fromMap(map: Map<String, Any?>): Badge {
            return Badge(
                map["typeOfBadge"] as String,
                map["moreInfo"] as String,
                map["imageUrls"] as List<String?>,
                map["name"] as String,
                map["collegeName"] as String?,
                map["time"] as String?
            )
        }
    }

}

