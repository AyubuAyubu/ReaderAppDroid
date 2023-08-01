package com.ayubu.qreader.model

data class MUser(
    val id:String?,
    val userId:String,
    var displayName:String,
    var avatarUrl:String,
    val quote:String,
    val profession:String
){
    fun toMap():MutableMap<String,Any>{
        return mutableMapOf(
            "user_id" to this.userId,
            "display_name" to this.displayName,
            "profession" to this.profession,
            "avatar_url" to this.avatarUrl
        )

    }
}
