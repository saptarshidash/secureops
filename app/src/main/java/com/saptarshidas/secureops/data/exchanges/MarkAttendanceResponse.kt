package com.saptarshidas.secureops.data.exchanges

data class MarkAttendanceResponse(
    val AttendanceDate: String,
    val AttendanceStatus: String,
    val AttendanceTime: Any,
    val CreatedBy: String,
    val EmpId: String,
    val Id: Int,
    val InTime: String,
    val Lat: Double,
    val Long: Double,
    val OutTime: Any,
    val PresentAbsent: String,
    val PresentOrAbsent: Any,
    val SelfiePath: Any,
    val SiteId: Int,
    val SiteName: String
)