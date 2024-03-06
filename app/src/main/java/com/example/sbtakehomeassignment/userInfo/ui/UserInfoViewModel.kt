package com.example.sbtakehomeassignment.userInfo.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.sbtakehomeassignment.common.base.BaseViewModel
import com.example.sbtakehomeassignment.userInfo.domain.UserInfoUseCase
import com.example.sbtakehomeassignment.userInfo.domain.models.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/**
 * Purpose: Acts as the ViewModel in the MVVM architecture for the user information feature. It interacts with the UserInfoUseCase to fetch user information and manages UI-related data states. It also handles saving and retrieving the search query state using SavedStateHandle.
 *
 * Functions:
 * getUserInfo()
 * Purpose: Initiates fetching user information based on the current search query stored in SavedStateHandle.
 * How It Works:
 * Retrieves the current search query from SavedStateHandle.
 * If the query is not empty, it calls userInfoUseCase.getUserInfo with the query.
 * Collects the flow of DataState<UserInfo> emitted by the use case.
 * For each state emitted, it updates the ViewModel's state to reflect loading, success, or error states.
 *
 * onSearchQueryChange(value: String)
 * Purpose: Updates the search query stored in SavedStateHandle.
 * How It Works:
 * Takes a string value and updates the SEARCH_QUERY key in SavedStateHandle with this value.
 *
 * searchQueryFlow(): Flow
 * Purpose: Exposes a flow of the current search query, allowing the UI to react to changes in the search query.
 * How It Works:
 * Returns a StateFlow representing the current search query by observing SEARCH_QUERY in SavedStateHandle, defaulting to an empty string if not present.
 * **/


@HiltViewModel
class UserInfoViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val userInfoUseCase: UserInfoUseCase,
) : BaseViewModel<UserInfo>(UserInfo()) {

    private val SEARCH_QUERY = "query"

    fun getUserInfo() {
        with(savedStateHandle.getLiveData<String>(SEARCH_QUERY).value) {
            if (this?.isNotEmpty() == true) {
                userInfoUseCase.getUserInfo(this).onEach { userInfoDataState ->
                    updateState(
                        requestProgressStatus = userInfoDataState.requestProgressStatus,
                        isLoading = userInfoDataState.isLoading,
                        errorMessage = userInfoDataState.errorMessage,
                        dataState = userInfoDataState.data ?: UserInfo()
                    )
                }.launchIn(viewModelScope)
            }
        }

    }

    fun onSearchQueryChange(value: String) {
        savedStateHandle[SEARCH_QUERY] = value
    }

    fun searchQueryFlow(): Flow<String> = savedStateHandle.getStateFlow(SEARCH_QUERY,"")

}