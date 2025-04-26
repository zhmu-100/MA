package com.zhmu100.ma.domain.model

import kotlinx.serialization.Serializable

/**
 * Запрос на авторизацию
 *
 * @property email почта
 * @property password пароль
 */

@Serializable
data class LoginRequest(
    val email: String,
    val password: String
)
