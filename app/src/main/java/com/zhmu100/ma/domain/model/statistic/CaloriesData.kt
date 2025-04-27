package com.zhmu100.ma.domain.model.statistic

/**
 * Данные о калориях
 * @property meta Метаданные пользователя
 * @property calories Количество калорий
 */
data class CaloriesData(
    val meta: CaloriesMetadata,
    val calories: Double
)