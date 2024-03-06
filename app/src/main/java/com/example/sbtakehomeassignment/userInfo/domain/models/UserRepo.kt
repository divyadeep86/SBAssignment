package com.example.sbtakehomeassignment.userInfo.domain.models

import com.example.sbtakehomeassignment.common.utils.formatNumber
import com.example.sbtakehomeassignment.userInfo.data.models.UserRepoRemote

data class UserRepo(
    val id: Int,
    val name: String,
    val avatarUrl:String,
    val forksCount: String,
    val watchCount: String,
    val starCount: String,
    val description: String?,
    val visibility:String
) {
    companion object {
        fun getMockData(): UserRepo {
            return UserRepo(
                avatarUrl = "",
                id = 20978623,
                name = "hello-worId",
                forksCount = "100",
                watchCount = "100",
                starCount = 10000015.formatNumber(),
                description = "This is test description",
                visibility = "Public"
            )
        }
    }


}

fun UserRepoRemote.toUserRepo(): UserRepo {
    return UserRepo(
        id = id,
        avatarUrl = owner.avatar_url,
        name = name,
        forksCount = forks_count?.formatNumber() ?:"0"  ,
        watchCount = watchers_count ?.formatNumber() ?:"0",
        starCount = stargazers_count ?.formatNumber() ?:"0",
        description = description,
        visibility = if(private) "Private" else "Public"
    )
}
