package com.zhmu100.ma.domain.model

import kotlinx.serialization.Serializable

/**
 * Запрос на регистрацию пользователя
 *
 * @property name имя
 * @property email почта
 * @property password пароль
 */

@Serializable
data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String
)
