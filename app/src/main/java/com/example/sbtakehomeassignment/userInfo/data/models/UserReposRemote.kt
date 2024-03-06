package com.example.sbtakehomeassignment.userInfo.data.models

import kotlinx.serialization.Serializable

@Serializable
data class UserRepoRemote(
    val description: String?,
    val forks: Int?,
    val forks_count: Int?,
    val full_name: String,
    val id: Int,
    val name: String,
    val owner: Owner,
    val url: String,
    val private:Boolean,
    val watchers_count:Int?,
    val stargazers_count:Int?,


)


@Serializable
data class Owner(
    val avatar_url: String,
    val id: Int,
    val type: String,
    val url: String
)
