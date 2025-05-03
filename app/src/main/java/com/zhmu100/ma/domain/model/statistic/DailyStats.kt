package com.zhmu100.ma.domain.model.statistic

import kotlinx.serialization.Serializable

/**
 * Данные о ежедневной активности пользователя.
 *
 * Хранит сводную информацию по физической активности и питанию за один день.
 * Используется в [StatisticViewModel] для отображения статистики за сегодняшний день и за неделю.
 *
 * @property date Дата в формате строки (например, "2025-04-05")
 * @property steps Количество шагов за день. По умолчанию: 0
 * @property distance Пройденное расстояние в метрах. По умолчанию: 0.0
 * @property calories Сожженные калории за день. По умолчанию: 0
 * @property protein Употребленный белок в граммах. По умолчанию: 0.0
 * @property carbs Употребленные углеводы в граммах. По умолчанию: 0.0
 * @property fats Употребленные жиры в граммах (насыщенные + трансжиры). По умолчанию: 0.0
 */
@Serializable
data class DailyStats(
    val date: String,
    val steps: Int = 0,
    val distance: Double = 0.0,
    val calories: Int = 0,
    val protein: Double = 0.0,
    val carbs: Double = 0.0,
    val fats: Double = 0.0
)