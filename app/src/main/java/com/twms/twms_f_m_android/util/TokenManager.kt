package com.twms.twms_f_m_android.util

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.twms.twms_f_m_android.di.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class TokenManager(private val context: Context) {
    companion object {
        private val TOKEN_KEY = stringPreferencesKey("jwt_token")
        private val COOKIE_KEY = stringPreferencesKey("cookie_token")
    }

    fun getToken(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[TOKEN_KEY]
        }
    }

    suspend fun saveToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
        }
    }

    suspend fun deleteToken() {
        context.dataStore.edit { preferences ->
            preferences.remove(TOKEN_KEY)
        }
    }

    fun getCookie(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[COOKIE_KEY]
        }
    }

    suspend fun saveCookie(cookie: String) {
        context.dataStore.edit { preferences ->
            preferences[COOKIE_KEY] = cookie
        }
    }

    suspend fun deleteCookie() {
        context.dataStore.edit { preferences ->
            preferences.remove(COOKIE_KEY)
        }
    }
}