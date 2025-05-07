package com.zhmu100.ma.domain.model.statistic

import kotlinx.serialization.Serializable

/**
 * Метаданные пользователя
 * @property id Уникальный идентификатор записи (опционально)
 * @property userId Идентификатор пользователя
 * @property timestamp Временная метка в формате ISO-8601
 */
@Serializable
data class UserMetadata(
    val id: String? = null,
    val userId: String,
    val timestamp: String
)