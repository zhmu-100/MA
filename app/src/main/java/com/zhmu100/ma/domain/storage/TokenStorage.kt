package com.zhmu100.ma.domain.storage

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import org.json.JSONObject
import android.util.Base64 as AndroidBase64

object TokenStorage {
    private const val PREF_NAME = "auth_prefs"
    private const val KEY_ACCESS_TOKEN = "access_token"
    private const val KEY_REFRESH_TOKEN = "refresh_token"
    private const val KEY_USER_ID = "sub"


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

    fun getUserId(): String {
        val token = getAccessToken() ?: throw IllegalStateException("Access token is null")
        val parts = token.split(".")
        if (parts.size < 2) throw IllegalStateException("Invalid token format")

        val payloadJson = String(
            AndroidBase64.decode(
                parts[1],
                AndroidBase64.URL_SAFE or AndroidBase64.NO_PADDING or AndroidBase64.NO_WRAP
            )
        )

        return JSONObject(payloadJson).getString("sub")
    }


    fun clearTokens() {
        prefs.edit() { clear() }
    }
}
