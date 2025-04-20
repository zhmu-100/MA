package com.zhmu100.ma.domain.model

import kotlinx.serialization.Serializable

/**
 * Ответ от сервиса авторизации
 *
 * @property accessToken - токен доступа
 * @property refreshToken - токен для обновления accessToken
 */

@Serializable
data class AuthResponse(
    val accessToken: String,
    val refreshToken: String
)
