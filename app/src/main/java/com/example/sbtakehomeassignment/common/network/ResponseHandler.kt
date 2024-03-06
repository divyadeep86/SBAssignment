package com.example.sbtakehomeassignment.common.network

sealed class ResponseHandler<out T>{
    data class Success<out T>(val data: T) : ResponseHandler<T>()
    data class Failure(val message:String) : ResponseHandler<Nothing>()
}