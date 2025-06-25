package com.zhmu100.ma.domain.model.diet

import kotlinx.serialization.Serializable

/**
 * Класс для хранения общей статистики питания за день.
 * @property calories Общее количество калорий
 * @property protein Общее количество белков (в граммах)
 * @property carbs Общее количество углеводов (в граммах)
 * @property fats Общее количество жиров (в граммах)
 */
@Serializable
data class DailyStats(
    var calories: Double = 0.0,
    var protein: Double = 0.0,
    var carbs: Double = 0.0,
    var fats: Double = 0.0
)