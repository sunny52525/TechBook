package com.example.techbook.domain.model

data class UserModel(
    val name: String = "",
    val id: String? = null,
    val iconUrl: String? = null,
    val college: String = "",
    val email: String = "",
    val badge: Badge? = null
) {
    fun toHashMap(): HashMap<String, Any?> {
        val map = HashMap<String, Any?>()
        map["name"] = name
        map["id"] = id
        map["iconUrl"] = iconUrl ?: ""
        map["college"] = college
        map["email"] = email
        map["badge"] = badge?.toMap()
        return map
    }

}
