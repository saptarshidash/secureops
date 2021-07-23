package com.saptarshidas.secureops.ui.auth

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.saptarshidas.secureops.base.BaseViewModel
import com.saptarshidas.secureops.data.exchanges.AuthRequest
import com.saptarshidas.secureops.data.exchanges.AuthResponse
import com.saptarshidas.secureops.data.network.Resource
import com.saptarshidas.secureops.data.repository.AuthRepository
import kotlinx.coroutines.launch

class AuthViewModel(application: Application): BaseViewModel(application) {

    private val repository = AuthRepository(application)

    var mutableAuthResponse: MutableLiveData<Resource<AuthResponse>> = MutableLiveData()

    var authLiveData: LiveData<Resource<AuthResponse>> = mutableAuthResponse

    fun login(request: AuthRequest) = viewModelScope.launch {
        mutableAuthResponse.value = repository.login(request)
    }

}