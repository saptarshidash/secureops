package com.saptarshidas.secureops.data.network.apis

import com.saptarshidas.secureops.data.exchanges.AuthResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthApi {

    @FormUrlEncoded
    @POST("api/homeapi/SpecificUser")
    suspend fun login(@Field("phone") username: String, @Field("pass") password: String)
    : AuthResponse

}