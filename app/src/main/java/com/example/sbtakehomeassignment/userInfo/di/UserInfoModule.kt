package com.example.sbtakehomeassignment.userInfo.di

import com.example.sbtakehomeassignment.userInfo.data.UserApi
import com.example.sbtakehomeassignment.userInfo.data.UserRepository
import com.example.sbtakehomeassignment.userInfo.domain.UserInfoUseCase
import com.example.sbtakehomeassignment.userInfo.domain.GetUserReposUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserInfoModule {

    @Singleton
    @Provides
    fun provideUserApi(retrofit: Retrofit): UserApi {
        return retrofit.create(UserApi::class.java)
    }
    @Singleton
    @Provides
    fun provideUserRepository(userApi: UserApi): UserRepository {
        return UserRepository(userApi = userApi)
    }

    @Singleton
    @Provides
    fun provideUserInfoUseCase(userRepository: UserRepository): UserInfoUseCase {
        return UserInfoUseCase(userRepository = userRepository)
    }

    @Singleton
    @Provides
    fun provideUserRepoUseCase(userRepository: UserRepository): GetUserReposUseCase {
        return GetUserReposUseCase(userRepository = userRepository)
    }


}