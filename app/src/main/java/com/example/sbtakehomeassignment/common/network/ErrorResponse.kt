package com.example.sbtakehomeassignment.common.network

import android.util.Log
import okhttp3.ResponseBody
import org.json.JSONObject

fun ResponseBody.getError():String{
    try {
        val jsonObject = JSONObject(this.string())
        return jsonObject.getString("message")
    } catch (ex: Exception) {
        Log.e("ExpInErrorParing-->", "${ex.message}")
        return ErrorConstant.ErrorParsingExp
    }
}
 object ErrorConstant{

    const val NetworkErrorMessage= "Network or conversion error"
    const val UnknownErrorMessage = "Unknown error"
     const val ErrorParsingExp ="Exception in parsing error"
}
