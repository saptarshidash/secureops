package com.saptarshidas.secureops.data.exchanges

data class MarkAttendanceRequest(
    val SiteName: String,
    val EmpId: String,
    val Lat: String,
    val Long: String,
    val CreatedBy: String
)
