package com.example.sbtakehomeassignment.userInfo.domain

import com.example.sbtakehomeassignment.common.network.ResponseHandler
import com.example.sbtakehomeassignment.common.states.DataState
import com.example.sbtakehomeassignment.userInfo.data.UserRepository
import com.example.sbtakehomeassignment.userInfo.domain.models.UserRepo
import com.example.sbtakehomeassignment.userInfo.domain.models.toUserRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
/**
 *
 * Purpose: Encapsulates the logic for fetching a list of repositories for a specific user from the UserRepository. It transforms the data received from the repository into a form that is usable by the UI layer, handling loading, success, and error states.
 *
 * Functions:
 * getUserRepos(userId: String): Flow<DataState<List >>
 * Purpose: Initiates the process of fetching user repositories and emits states representing the progress: loading, success with data, or failure with an error message.
 * How It Works:
 * Starts by emitting a loading state to indicate the start of the data fetch operation.
 * Calls userRepository.getUserRepos to fetch the repositories.
 * On success (ResponseHandler.Success), maps the data from the network model to the domain model and emits a success state with the mapped data.
 * On failure (ResponseHandler.Failure), emits an error state with the error message.
 * */
class GetUserReposUseCase(private val userRepository: UserRepository) {
    fun getUserRepos(userId: String): Flow<DataState<List<UserRepo>>> = flow {
        // Emit loading state before making the request
        emit(DataState.loading(true))
        when (val response = userRepository.getUserRepos(userId)) {
            // Success case: emit success state
            is ResponseHandler.Success -> {
                emit(DataState.success(data = response.data.map { it.toUserRepo() }))
            }

            is ResponseHandler.Failure -> {
                // Failure case: emit error state with message
                emit(DataState.error(response.message))
            }
        }
    }
}