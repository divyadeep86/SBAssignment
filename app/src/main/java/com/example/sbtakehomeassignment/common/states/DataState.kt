package com.example.sbtakehomeassignment.common.states

data class DataState<T>(
    val data: T? = null,
    val success: Boolean = false,
    val isLoading: Boolean = false,
    val message: String? = null,
    val errorMessage: String? = null,
    val requestProgressStatus: RequestProgressStatus = RequestProgressStatus.Idle
) {

    companion object {
        fun <T> loading(isLoading: Boolean): DataState<T> {
            return DataState(
                isLoading = isLoading,
                requestProgressStatus = if (isLoading) RequestProgressStatus.InProgress else RequestProgressStatus.Completed
            )
        }


        fun <T> success(
            data: T?
        ): DataState<T> {
            return DataState(
                data = data, success = true, requestProgressStatus = RequestProgressStatus.Completed
            )
        }


        fun <T> error(error_message: String): DataState<T> {
            return DataState(
                success = false,
                errorMessage = error_message,
                requestProgressStatus = RequestProgressStatus.Completed
            )
        }
    }
}

enum class RequestProgressStatus {
    Idle, InProgress, Completed
}
