package com.saptarshidas.secureops.data.exchanges

data class AuthResponse(
    val ClientName: String,
    val Designation: String,
    val EmpCode: String,
    val Name: String,
    val Password: String,
    val LoginStatus: String
)