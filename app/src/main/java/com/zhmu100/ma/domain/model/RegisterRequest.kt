package com.zhmu100.ma.domain.model

import kotlinx.serialization.Serializable

/**
 * Запрос на регистрацию пользователя
 *
 * @property username имя
 * @property email почта
 * @property password пароль
 */

@Serializable
data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String
)
