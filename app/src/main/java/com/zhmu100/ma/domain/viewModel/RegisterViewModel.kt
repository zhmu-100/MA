package com.zhmu100.ma.domain.viewModel

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhmu100.ma.domain.api.auth.AuthApiImpl
import com.zhmu100.ma.domain.storage.TokenStorage
import com.zhmu100.ma.domain.Network
import com.zhmu100.ma.domain.model.RegisterRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    private val api = AuthApiImpl(Network.httpClient)

    private val _message = MutableStateFlow<String?>(null)
    val message = _message.asStateFlow()

    private val _isRegisterSuccessful = MutableStateFlow(false)
    val isRegisterSuccessful = _isRegisterSuccessful.asStateFlow()

    fun register(name: String, email: String, password: String, confirmPassword: String) {
        when {
            email.isBlank() || password.isBlank() || name.isBlank()-> {
                _message.value = "Fields must not be empty"
                _isRegisterSuccessful.value = false
                return
            }

            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                _message.value = "Uncorrected email"
                _isRegisterSuccessful.value = false
                return
            }

            password != confirmPassword -> {
                _message.value = "Password don't match"
                return
            }
        }

        viewModelScope.launch {
            runCatching {
                api.register(RegisterRequest(name, email, password))
            }.onSuccess {
                TokenStorage.saveAccessToken(it.accessToken)
                TokenStorage.saveRefreshToken(it.refreshToken)
                _isRegisterSuccessful.value = true
            }.onFailure {
                _message.value = "Registration error: ${it.message}"
            }
        }
    }
}