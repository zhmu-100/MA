package com.zhmu100.ma.domain.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhmu100.ma.domain.api.auth.AuthApiImpl
import com.zhmu100.ma.domain.model.LoginRequest
import com.zhmu100.ma.domain.storage.TokenStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authApiImpl: AuthApiImpl
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
                authApiImpl.login(LoginRequest(email, password))
            }.onSuccess {
                TokenStorage.saveAccessToken(it.accessToken)
                TokenStorage.saveRefreshToken(it.refreshToken)
                _isLoginSuccessful.value = true
            }.onFailure {
                _message.value = "Login error: ${it.message}"
                _isLoginSuccessful.value = false
            }
        }
    }
}