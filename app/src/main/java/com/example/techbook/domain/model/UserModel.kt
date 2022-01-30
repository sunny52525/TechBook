package com.example.techbook.domain.model

data class UserModel(
    val name:String,
    val id:String?=null,
    val iconUrl:String?=null,
    val college:String,
    val email:String,
){
    fun toHashMap():HashMap<String,String?>{
        val map = HashMap<String,String?>()
        map["name"] = name
        map["id"] = id
        map["iconUrl"] = iconUrl ?: ""
        map["college"] = college
        map["email"] = email
        return map
    }
}
