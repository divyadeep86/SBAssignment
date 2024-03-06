package com.example.sbtakehomeassignment.userInfo.data.models

import kotlinx.serialization.Serializable

@Serializable
data class UserInfoRemote(
    val avatar_url: String?,
    val id: Int,
    val login: String?,
    val updated_at: String?,
    val url: String?,
    val name:String?
)