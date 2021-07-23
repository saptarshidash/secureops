package com.saptarshidas.secureops.data.repository

import android.content.Context
import com.saptarshidas.secureops.base.BaseRepository
import com.saptarshidas.secureops.data.exchanges.AuthRequest
import com.saptarshidas.secureops.data.network.RemoteDataSource
import com.saptarshidas.secureops.data.network.apis.AuthApi

class AuthRepository(context: Context): BaseRepository() {

    private var authApi: AuthApi = RemoteDataSource.createApi(AuthApi::class.java)

    suspend fun login(request: AuthRequest) = safeApiCall {
        authApi.login(request.username, request.password)
    }
}