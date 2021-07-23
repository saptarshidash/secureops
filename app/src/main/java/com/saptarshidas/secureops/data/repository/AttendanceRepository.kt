package com.saptarshidas.secureops.data.repository

import android.content.Context
import com.saptarshidas.secureops.base.BaseRepository
import com.saptarshidas.secureops.data.exchanges.MarkAttendanceRequest
import com.saptarshidas.secureops.data.network.RemoteDataSource
import com.saptarshidas.secureops.data.network.apis.AttendanceApi
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class AttendanceRepository(context: Context): BaseRepository() {

    private var attendanceApi: AttendanceApi = RemoteDataSource.createApi(AttendanceApi::class.java)

    suspend fun markAttendance(request: MarkAttendanceRequest) = safeApiCall {
        attendanceApi.savePollBoothAttendance(
            request.SiteName.toRequestBody(MultipartBody.FORM),
            request.EmpId.toRequestBody(MultipartBody.FORM),
            request.Lat.toRequestBody(MultipartBody.FORM),
            request.Long.toRequestBody(MultipartBody.FORM),
            request.CreatedBy.toRequestBody(MultipartBody.FORM)
        )
    }
}