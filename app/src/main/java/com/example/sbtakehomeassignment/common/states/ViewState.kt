package com.example.sbtakehomeassignment.common.states

data class ViewState<T>(
    val isLoading: Boolean = false,
    var dataState: T? = null,
    val message: String? = null,
    val errorMessage: String? = null,
    val requestProgressStatus: RequestProgressStatus = RequestProgressStatus.Idle
)
