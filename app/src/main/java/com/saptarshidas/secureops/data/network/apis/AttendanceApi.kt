package com.saptarshidas.secureops.data.network.apis

import com.saptarshidas.secureops.data.exchanges.MarkAttendanceResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface AttendanceApi {

    @Multipart
    @POST("api/TransactionApi/SetAttendance")
    suspend fun savePollBoothAttendance(
        @Part("SiteName") siteName: RequestBody?,
        @Part("EmpId") empId: RequestBody?,
        @Part("Lat") lat: RequestBody?,
        @Part("Long") long: RequestBody?,
        @Part("CreatedBy") createdBy: RequestBody?
        //@Part photo: MultipartBody.Part
    ): MarkAttendanceResponse
}