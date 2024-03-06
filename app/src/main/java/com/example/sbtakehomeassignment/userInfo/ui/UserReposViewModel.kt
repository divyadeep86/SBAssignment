package com.example.sbtakehomeassignment.userInfo.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.asFlow
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.sbtakehomeassignment.common.base.BaseViewModel
import com.example.sbtakehomeassignment.userInfo.domain.GetUserReposUseCase
import com.example.sbtakehomeassignment.userInfo.domain.models.UserRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/**
 * Purpose: Manages the UI-related data for user repositories, interacting with GetUserReposUseCase to fetch repositories based on a user ID. It observes changes to the user ID and repository selection, updating the UI state accordingly.
 *
 * Functions:
 * init block
 * Purpose: Initializes the ViewModel by setting up an observer on the user ID.
 * How It Works: Calls observeUserId() to start observing changes to the user ID stored in SavedStateHandle.
 *
 *  observeUserId()
 * Purpose: Observes changes to the user ID, debounces rapid changes, and fetches user repositories for the new user ID.
 * How It Works:
 * Converts the LiveData holding the user ID into a Flow.
 * Filters out empty values and debounces to mitigate rapid updates.
 * Uses flatMapLatest to fetch repositories whenever the user ID changes, ensuring only the latest request is processed if multiple requests are made in quick succession.
 * Updates the ViewModel's state with loading, success, or error information based on the response from userReposUseCase.
 *
 * setUserId(userId: String)
 * Purpose: Updates the user ID in SavedStateHandle.
 * How It Works: Stores the provided user ID in SavedStateHandle, triggering the observer set up in observeUserId().
 *
 * setRepoId(repoID: Int)
 * Purpose: Updates the selected repository ID in SavedStateHandle.
 * How It Works: Stores the provided repository ID in SavedStateHandle, allowing the UI to react to changes in the selected repository.
 *
 * getRepo(): Flow<UserRepo?>
 * Purpose: Retrieves the currently selected repository as a Flow.
 * How It Works:
 * Maps the LiveData holding the selected repository ID to a Flow.
 * Transforms each emitted repository ID into the corresponding UserRepo object by searching the current list of repositories in the ViewModel's state.
 * Returns a Flow that emits the selected repository or null if it's not found.
 *
 * */
@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class UserReposViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle, // 1
    private val userReposUseCase: GetUserReposUseCase
) : BaseViewModel<List<UserRepo>>(listOf()) {
    private val USERID = "userId"
    private val SELECTED_REPOID = "repoID"

    init {
        observeUserId()
    }

    @OptIn(FlowPreview::class)
    private fun observeUserId() {
        savedStateHandle.getLiveData(USERID, "").asFlow().filter {
            it.isNotEmpty()
        }.debounce(300).flatMapLatest {
            userReposUseCase.getUserRepos(it)
        }.onEach { userInfoDataState ->
            updateState(
                requestProgressStatus = userInfoDataState.requestProgressStatus,
                isLoading = userInfoDataState.isLoading, // 8
                errorMessage = userInfoDataState.errorMessage, // 9 ,
                dataState = userInfoDataState.data ?: listOf()// 10
            )
        }.launchIn(viewModelScope)
    }

    fun setUserId(userId: String) {
        savedStateHandle[USERID] = userId
    }

    fun setRepoId(repoID: Int) {
        savedStateHandle[SELECTED_REPOID] = repoID
    }

    fun getRepo(): Flow<UserRepo?> = savedStateHandle.getLiveData(SELECTED_REPOID, 0)
        .map { viewState.value.dataState?.single { userRepo -> userRepo.id == it } }.asFlow()
}