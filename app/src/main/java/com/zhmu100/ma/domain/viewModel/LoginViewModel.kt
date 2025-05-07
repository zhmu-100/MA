package com.zhmu100.ma.domain.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhmu100.ma.domain.api.auth.AuthApi
import com.zhmu100.ma.domain.model.LoginRequest
import com.zhmu100.ma.domain.storage.MessageManager
import com.zhmu100.ma.domain.storage.TokenStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authApi: AuthApi,
    private val tokenStorage: TokenStorage,
    private val messageManager: MessageManager
) : ViewModel() {
    private val _message = MutableStateFlow<String?>(null)
    val message = _message.asStateFlow()

    private val _isLoginSuccessful = MutableStateFlow(false)
    val isLoginSuccessful = _isLoginSuccessful.asStateFlow()

    fun clearMessage() {
        _message.value = null
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            if (email.isBlank() || password.isBlank()) {
                _message.value = "Все поля должны быть заполнены"
                _isLoginSuccessful.value = false
                messageManager.emitMessage("Все поля должны быть заполнены")
                return@launch
            }

            runCatching {
                authApi.login(LoginRequest(email, password))
            }.onSuccess {
                tokenStorage.saveAccessToken(it.accessToken)
                tokenStorage.saveRefreshToken(it.refreshToken)
                _isLoginSuccessful.value = true
            }.onFailure {
                _message.value = "Неверный логин или пароль"
                _isLoginSuccessful.value = false
                messageManager.emitMessage("Неверный логин или пароль")
            }
        }
    }
}