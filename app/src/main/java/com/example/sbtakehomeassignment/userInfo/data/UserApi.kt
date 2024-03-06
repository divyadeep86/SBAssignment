package com.example.sbtakehomeassignment.userInfo.data

import com.example.sbtakehomeassignment.userInfo.data.models.UserInfoRemote
import com.example.sbtakehomeassignment.userInfo.data.models.UserRepoRemote
import retrofit2.http.GET
import retrofit2.http.Path

interface UserApi {
    @GET("users/{userId}")
    suspend fun getUserInfo(@Path("userId") userId: String): UserInfoRemote

    @GET("users/{userId}/repos")
    suspend fun getUserRepos(@Path("userId") userId: String): List<UserRepoRemote>
}