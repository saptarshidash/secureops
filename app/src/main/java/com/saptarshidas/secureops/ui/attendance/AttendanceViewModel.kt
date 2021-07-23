package com.saptarshidas.secureops.ui.attendance

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.saptarshidas.secureops.base.BaseViewModel
import com.saptarshidas.secureops.data.exchanges.MarkAttendanceRequest
import com.saptarshidas.secureops.data.exchanges.MarkAttendanceResponse
import com.saptarshidas.secureops.data.network.Resource
import com.saptarshidas.secureops.data.repository.AttendanceRepository
import kotlinx.coroutines.launch

class AttendanceViewModel(application: Application): BaseViewModel(application) {

    private val repository = AttendanceRepository(application)

    private var getMarkAttendance: MutableLiveData<Resource<MarkAttendanceResponse>> = MutableLiveData()

    var markAttendanceLiveData: LiveData<Resource<MarkAttendanceResponse>> = getMarkAttendance

    fun markAttendance(request: MarkAttendanceRequest) = viewModelScope.launch {
        getMarkAttendance.value = repository.markAttendance(request)
    }
}