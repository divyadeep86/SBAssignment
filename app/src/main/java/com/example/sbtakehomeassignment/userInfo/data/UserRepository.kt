package com.example.sbtakehomeassignment.userInfo.data

import android.util.Log
import com.example.sbtakehomeassignment.common.network.ErrorConstant
import com.example.sbtakehomeassignment.common.network.ResponseHandler
import com.example.sbtakehomeassignment.common.network.getError
import com.example.sbtakehomeassignment.userInfo.data.models.UserInfoRemote
import com.example.sbtakehomeassignment.userInfo.data.models.UserRepoRemote
import retrofit2.HttpException
import java.io.IOException

/**
 * Purpose: Acts as a data layer in the application architecture, responsible for fetching user information and repositories from a remote server via the UserApi interface. It handles success and various exceptions to provide a unified response type to the calling code.
 *
 * Functions:
 *
 * getUserInfo(userId: String): ResponseHandler
 * Purpose: Fetches detailed information about a user by their ID.
 * How It Works:
 * Makes a network request through userApi.getUserInfo.
 * On success, wraps the response in ResponseHandler.Success.
 * Handles HttpException for HTTP errors, IOException for network or IO issues, and general Exception for other errors, wrapping them in ResponseHandler.Failure with appropriate error messages.
 *
 * getUserRepos(userId: String): ResponseHandler<List >
 * Purpose: Fetches a list of repositories owned by a user.
 * How It Works:
 * Makes a network request through userApi.getUserRepos.
 * On success, wraps the response in ResponseHandler.Success.
 * Handles exceptions (HttpException, IOException, and Exception) similarly to getUserInfo, providing a unified way to handle errors and successful responses.
 *
 * */

class UserRepository(private val userApi: UserApi) {
    suspend fun getUserInfo(userId: String): ResponseHandler<UserInfoRemote> {
        return try {
            val response = userApi.getUserInfo(userId)
            // Success handling: Wrap API response in Success
            ResponseHandler.Success(response)
        } catch (e: HttpException) {
            Log.e("getUserInfo:HttpException-->", e.message())
            // HTTP exception handling: Wrap message in Failure
            ResponseHandler.Failure(
                message = e.response()?.errorBody()?.getError() ?: ErrorConstant.ErrorParsingExp
            )
        } catch (e: IOException) {
            Log.e("getUserInfo:IOException-->", "${e.message}")
            // Network/IO exception handling: Wrap network error message in Failure
            ResponseHandler.Failure(ErrorConstant.NetworkErrorMessage)
        } catch (e: Exception) {
            Log.e("getUserInfo:Exp-->", e.localizedMessage)
            // General exception handling: Wrap unknown error message in Failure
            ResponseHandler.Failure(e.localizedMessage)

        }
    }

    suspend fun getUserRepos(userId: String): ResponseHandler<List<UserRepoRemote>> {
        return try {
            val response = userApi.getUserRepos(userId)
            // Success handling: Wrap API response in Success
            ResponseHandler.Success(response)
        } catch (e: HttpException) {
            Log.e("getUserRepos:HttpException-->", e.message())
            // HTTP exception handling: Wrap message in Failure
            ResponseHandler.Failure(e.message())
        } catch (e: IOException) {
           Log.e("getUserRepos:IOException-->", "${e.message}")
            // Network/IO exception handling: Wrap network error message in Failure
            ResponseHandler.Failure(ErrorConstant.NetworkErrorMessage)
        } catch (e: Exception) {
            Log.e("getUserRepos:Exp-->", "${e.message}")
            // General exception handling: Wrap unknown error message in Failure
            ResponseHandler.Failure(ErrorConstant.UnknownErrorMessage)
        }
    }


}