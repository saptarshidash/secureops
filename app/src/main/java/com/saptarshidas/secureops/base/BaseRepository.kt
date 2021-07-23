package com.saptarshidas.secureops.base

import com.saptarshidas.secureops.data.network.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import retrofit2.HttpException

abstract class BaseRepository {

    // passing api call lamda fun to this function

    private val TAG = "BaseRepository"

    suspend fun <T> safeApiCall(apiCall: suspend()-> T) : Resource<T> {

        return withContext(Dispatchers.IO){

            try {

                Resource.Success(apiCall.invoke())

            }catch (throwable: Throwable){
                when(throwable){
                    is HttpException ->
                        Resource.Error(false, throwable.code(), throwable.response()?.errorBody())

                    else ->
                        Resource.Error(true, null, null, throwable.message)
                }
            }
        }
    }

}