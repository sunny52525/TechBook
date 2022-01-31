package com.example.techbook.domain.model

import com.example.techbook.common.Constants.DUMMY_IMAGE

data class UserModel(
    val name: String = "",
    val id: String? = null,
    val iconUrl: String? = DUMMY_IMAGE,
    val college: String = "",
    val email: String = "",
    val year : String? = "",
) {
    fun toHashMap(): HashMap<String, Any?> {
        val map = HashMap<String, Any?>()
        map["name"] = name
        map["id"] = id
        map["iconUrl"] = iconUrl ?: ""
        map["college"] = college
        map["email"] = email
        map["year"] = year
        return map
    }

    companion object {

        fun fromMap(map: Map<String, Any?>): UserModel {
            return UserModel(
                name = map["name"] as String,
                id = map["id"] as String?,
                iconUrl = map["iconUrl"] as String?,
                college = map["college"] as String,
                email = map["email"] as String,
                year = map["year"] as String
            )
        }
    }

}
