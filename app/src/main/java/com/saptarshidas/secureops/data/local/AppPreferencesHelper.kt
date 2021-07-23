package com.saptarshidas.secureops.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("user_preferences")

class AppPreferencesHelper(context: Context) : PreferencesHelper {

    private val applicationContext = context.applicationContext

    private val dataStore: DataStore<Preferences> = applicationContext.dataStore

    companion object {
        private val PREF_KEY_NAME = stringPreferencesKey("name")
        private val PREF_KEY_EMPlOYEE_CODE = stringPreferencesKey("emp_code")
        private val PREF_KEY_CLIENT_NAME= stringPreferencesKey("client_name")
        private val PREF_KEY_DESIGNATION = stringPreferencesKey("designation")

    }


    override suspend fun setName(name: String?) {
        dataStore.edit { user -> user[PREF_KEY_NAME] = name.toString() }
    }

    override fun getName(): Flow<String?> {
        return dataStore.data.map { preferences -> preferences[PREF_KEY_NAME] }
    }

    override suspend fun setEmployeeId(empId: String?) {
        dataStore.edit { id -> id[PREF_KEY_EMPlOYEE_CODE] = empId.toString() }
    }

    override fun getEmployeeId(): Flow<String?> {
        return dataStore.data.map { preferences -> preferences[PREF_KEY_EMPlOYEE_CODE] }
    }

    override suspend fun setClientName(name: String?) {
        dataStore.edit { id -> id[PREF_KEY_CLIENT_NAME] = name.toString() }
    }

    override fun getClientName(): Flow<String?> {
        return dataStore.data.map { preferences -> preferences[PREF_KEY_CLIENT_NAME]  }
    }

    override suspend fun setDesignation(name: String?) {
        dataStore.edit { id -> id[PREF_KEY_DESIGNATION] = name.toString() }
    }

    override fun getDesignation(): Flow<String?> {
        return dataStore.data.map { preferences -> preferences[PREF_KEY_DESIGNATION]  }
    }

}