package com.zhmu100.ma.domain.model

import kotlinx.serialization.Serializable

/**
 * Запрос на авторизацию
 *
 * @property username логин
 * @property password пароль
 */

@Serializable
data class LoginRequest(
    val username: String,
    val password: String
)
