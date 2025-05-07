package com.zhmu100.ma.domain.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zhmu100.ma.domain.api.auth.AuthApi
import com.zhmu100.ma.domain.storage.TokenStorage

class AuthViewModel(
    private val authApi: AuthApi,
    private val tokenStorage: TokenStorage
) : ViewModel() {
    private val _isTokenValid = MutableLiveData<Boolean>()
    val isTokenValid: LiveData<Boolean> get() = _isTokenValid

    private val _accessToken = MutableLiveData<String>()
    val accessToken: LiveData<String> get() = _accessToken

    private val _logoutSuccess = MutableLiveData<Boolean>()
    val logoutSuccess: LiveData<Boolean> get() = _logoutSuccess

    suspend fun validateToken(): Boolean {
        return tokenStorage.getAccessToken() != null
//        return try {
//            val result = authApi.validate()
//            result
//        } catch (e: Exception) {
//            false
//        }
    }

    suspend fun refreshToken() {
//        val refreshToken = tokenStorage.getRefreshToken()
//        if (!refreshToken.isNullOrBlank()) {
//            try {
//                val response = authApi.refresh(refreshToken)
//                tokenStorage.saveAccessToken(response.accessToken)
//                _accessToken.value = response.accessToken
//            } catch (e: Exception) {
//                e.message
//            }
//        }
    }

    suspend fun logout() {
        try {
            authApi.logout()
            tokenStorage.clearTokens()
            _logoutSuccess.value = true
        } catch (e: Exception) {
            _logoutSuccess.value = false
        }
    }
}
