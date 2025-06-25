package com.zhmu100.ma.domain.model.statistic

import kotlinx.serialization.Serializable

/**
 * Метаданные упражнения
 * @property id Уникальный идентификатор записи (опционально)
 * @property exerciseId Идентификатор упражнения
 * @property timestamp Временная метка снятия данных в формате ISO-8601
 */
@Serializable
data class ExerciseMetadata(
    val id: String? = null,
    val exerciseId: String,
    val timestamp: String
)