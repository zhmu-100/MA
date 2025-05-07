package com.zhmu100.ma.domain.viewModel

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhmu100.ma.domain.api.auth.AuthApi
import com.zhmu100.ma.domain.api.profile.ProfileApi
import com.zhmu100.ma.domain.model.LoginRequest
import com.zhmu100.ma.domain.model.RegisterRequest
import com.zhmu100.ma.domain.model.profile.Birthdate
import com.zhmu100.ma.domain.model.profile.UserProfile
import com.zhmu100.ma.domain.storage.MessageManager
import com.zhmu100.ma.domain.storage.TokenStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val authApi: AuthApi,
    private val tokenStorage: TokenStorage,
    private val profileApi: ProfileApi,
    private val messageManager: MessageManager
) : ViewModel() {
    private val _message = MutableStateFlow<String?>(null)
    val message = _message.asStateFlow()

    private val _isRegisterSuccessful = MutableStateFlow(false)
    val isRegisterSuccessful = _isRegisterSuccessful.asStateFlow()

    fun register(name: String, email: String, password: String, confirmPassword: String) {
        viewModelScope.launch {
        when {
            email.isBlank() || password.isBlank() || name.isBlank() -> {
                _message.value = "Fields must not be empty"
                messageManager.emitMessage("Поля должны быть заполены")
                _isRegisterSuccessful.value = false
                return@launch
            }

            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                _message.value = "Uncorrected email"
                messageManager.emitMessage("Неверный формат почты")
                _isRegisterSuccessful.value = false
                return@launch
            }

            password != confirmPassword -> {
                _message.value = "Password don't match"
                messageManager.emitMessage("Пароли не совпадают")
                return@launch
            }
        }

            runCatching {
                authApi.register(RegisterRequest(name, email, password))
                authApi.login(LoginRequest(name, password))
            }.onSuccess {
                tokenStorage.saveAccessToken(it.accessToken)
                tokenStorage.saveRefreshToken(it.refreshToken)
                val profile = UserProfile(
                    id = tokenStorage.getUserId(),
                    user_id = tokenStorage.getUserId(),
                    name = name,
                    email = email,
                    birthdate = Birthdate(1991, 2, 2)
                )
                val result = profileApi.createProfile(profile)
                Log.d(result.toString(), "result :${result}")
                _isRegisterSuccessful.value = true
            }.onFailure {
                messageManager.emitMessage("Ошибка регистрации")
                _message.value = "Registration error: ${it.message}"
            }
        }
    }
}