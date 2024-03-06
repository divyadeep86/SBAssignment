package com.example.sbtakehomeassignment.userInfo.domain.models

import com.example.sbtakehomeassignment.userInfo.data.models.UserInfoRemote

data class UserInfo(val id: Int=0, val avatar_url: String="", val name: String=""){
    companion object {
        fun getMockData(): UserInfo {
            return UserInfo(
                id = 20978623,
                name = "hello-worId",
                avatar_url = "https://avatars.githubusercontent.com/u/583231?v=4",
            )
        }
    }
}


fun UserInfoRemote.toUserInfo(): UserInfo {
    return UserInfo(id = id, avatar_url = avatar_url?:"", name = name?:"")
}