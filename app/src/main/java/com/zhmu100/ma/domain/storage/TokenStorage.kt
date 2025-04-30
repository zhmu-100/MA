package com.zhmu100.ma.domain.storage

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

object TokenStorage {
    private const val PREF_NAME = "auth_prefs"
    private const val KEY_ACCESS_TOKEN = "access_token"
    private const val KEY_REFRESH_TOKEN = "refresh_token"

    private lateinit var prefs: SharedPreferences

    fun init(context: Context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveAccessToken(token: String) {
        prefs.edit() { putString(KEY_ACCESS_TOKEN, token) }
    }

    fun saveRefreshToken(token: String) {
        prefs.edit() { putString(KEY_REFRESH_TOKEN, token) }
    }

    fun getAccessToken(): String? = prefs.getString(KEY_ACCESS_TOKEN, null)
    fun getRefreshToken(): String? = prefs.getString(KEY_REFRESH_TOKEN, null)

    fun clearTokens() {
        prefs.edit() { clear() }
    }
}
