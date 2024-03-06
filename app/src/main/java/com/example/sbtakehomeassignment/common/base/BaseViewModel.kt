package com.example.sbtakehomeassignment.common.base

import androidx.lifecycle.ViewModel
import com.example.sbtakehomeassignment.common.states.RequestProgressStatus
import com.example.sbtakehomeassignment.common.states.ViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * BaseViewModel is a generic ViewModel class that provides a structured approach to managing and updating the state of a UI component.
 * It uses MutableStateFlow to emit the current state to observers.
 * The state consists of isLoading flag, an optional message, and the actual data of type T.
 * This class allows updating the state with new data, loading status, and optional message, as well as clearing the message when needed.
 */
abstract class BaseViewModel<T>(initialValue: T) : ViewModel() {
    private val _viewState = MutableStateFlow(ViewState(dataState = initialValue))
    val viewState = _viewState.asStateFlow()

    /**
     * Update the state with new loading status, an optional message, and new data.
     * If dataState is not provided, the existing data will be retained.
     */
    fun updateState(
        isLoading: Boolean = false,
        requestProgressStatus: RequestProgressStatus = RequestProgressStatus.Idle,
        errorMessage: String? = null,
        dataState: T? = null
    ) {
        _viewState.update {
            it.copy(
                isLoading = isLoading,
                requestProgressStatus = requestProgressStatus,
                errorMessage = errorMessage,
                dataState = dataState
            )
        }

    }

    fun updateLoadState(isLoading: Boolean, message: String?) {
        _viewState.update {
            it.copy(isLoading = isLoading, message = message)
        }
    }

    /**
     * Clear all messages in the state by setting it to null.
     */
    fun clearAllMessages() {
        _viewState.update {
            it.copy(errorMessage = null, message = null)
        }
    }
}