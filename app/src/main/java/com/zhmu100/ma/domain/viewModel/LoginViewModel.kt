package com.zhmu100.ma.domain.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhmu100.ma.domain.api.auth.AuthApi
import com.zhmu100.ma.domain.model.LoginRequest
import com.zhmu100.ma.domain.storage.TokenStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authApi: AuthApi,
    private val tokenStorage: TokenStorage
) : ViewModel() {
    private val _message = MutableStateFlow<String?>(null)
    val message = _message.asStateFlow()

    private val _isLoginSuccessful = MutableStateFlow(false)
    val isLoginSuccessful = _isLoginSuccessful.asStateFlow()

    fun login(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _message.value = "Fields must not be empty"
            _isLoginSuccessful.value = false
            return
        }

        viewModelScope.launch {
            runCatching {
                authApi.login(LoginRequest(email, password))
            }.onSuccess {
                tokenStorage.saveAccessToken(it.accessToken)
                tokenStorage.saveRefreshToken(it.refreshToken)
                _isLoginSuccessful.value = true
            }.onFailure {
                _message.value = "Login error: ${it.message}"
                _isLoginSuccessful.value = false
            }
        }
    }
}