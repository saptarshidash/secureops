package com.saptarshidas.secureops.data.local

import kotlinx.coroutines.flow.Flow

interface PreferencesHelper {

    suspend fun  setName(name: String?)
    fun getName(): Flow<String?>

    suspend fun setEmployeeId(empId: String?)
    fun getEmployeeId(): Flow<String?>

    suspend fun setClientName(name: String?)
    fun getClientName(): Flow<String?>

    suspend fun setDesignation(name: String?)
    fun getDesignation(): Flow<String?>
}