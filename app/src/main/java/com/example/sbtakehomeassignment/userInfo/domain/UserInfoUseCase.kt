package com.example.sbtakehomeassignment.userInfo.domain

import com.example.sbtakehomeassignment.common.network.ResponseHandler
import com.example.sbtakehomeassignment.common.states.DataState
import com.example.sbtakehomeassignment.userInfo.data.UserRepository
import com.example.sbtakehomeassignment.userInfo.domain.models.UserInfo
import com.example.sbtakehomeassignment.userInfo.domain.models.toUserInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 *
 * Purpose: Manages the operation of fetching detailed user information from the UserRepository. It processes the response to emit states that indicate loading, success, or failure, facilitating the communication between the repository layer and UI.
 * Functions:
 * getUserInfo(userId: String): Flow<DataState<UserInfo>>
 * Purpose: Initiates fetching user information for a given user ID and emits states to represent different stages of the process.
 * How It Works:
 * Begins by emitting a loading state to signal the start of the data retrieval process.
 * Calls userRepository.getUserInfo to obtain user information.
 * On receiving a successful response (ResponseHandler.Success), it converts the data from the data model to the domain model and emits a success state with this data.
 * In case of a failure (ResponseHandler.Failure), it emits an error state containing the error message. This allows the UI layer to react accordingly to success or failure scenarios.
 * */

class UserInfoUseCase(private val userRepository: UserRepository) {

    fun getUserInfo(userId: String): Flow<DataState<UserInfo>> = flow {
        // Emit loading state before making the request
        emit(DataState.loading(true))

        when (val response = userRepository.getUserInfo(userId)) {
            // Success case: emit success state
            is ResponseHandler.Success -> {
                emit(DataState.success(data = response.data.toUserInfo()))
            }
            is ResponseHandler.Failure -> {
                // Failure case: emit error state with message
                emit(DataState.error(response.message))
            }
        }
    }
}