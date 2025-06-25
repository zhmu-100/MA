package com.zhmu100.ma.domain.model.statistic

import kotlinx.serialization.Serializable

/**
 * Данные о калориях
 * @property meta Метаданные пользователя
 * @property calories Количество калорий
 */
@Serializable
data class CaloriesData(
    val meta: UserMetadata,
    val calories: Double
)