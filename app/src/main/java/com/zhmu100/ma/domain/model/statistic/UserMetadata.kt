package com.zhmu100.ma.domain.model.statistic

/**
 * Метаданные пользователя
 * @property id Уникальный идентификатор записи (опционально)
 * @property userId Идентификатор пользователя
 * @property timestamp Временная метка в формате ISO-8601
 */
data class UserMetadata(
    val id: String? = null,
    val userId: String,
    val timestamp: String
)