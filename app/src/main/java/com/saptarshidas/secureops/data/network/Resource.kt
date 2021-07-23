package com.saptarshidas.secureops.data.network

import okhttp3.ResponseBody

sealed class Resource<out T>{

    data class Success<T>(val data: T): Resource<T>()

    data class Error(
        val isNetworkError: Boolean,
        val errorCode: Int?,
        val errorBody: ResponseBody?,
        val msg: String? = ""
    ) : Resource<Nothing>()

    object Loading : Resource<Nothing>() {

    }
}