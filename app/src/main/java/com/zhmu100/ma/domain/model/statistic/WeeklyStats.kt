package com.zhmu100.ma.domain.model.statistic

import java.time.LocalDate

/**
 * Содержит сводную статистику за день по комбинации тренировок и питания.
 *
 * Используется в [StatisticViewModel] для отображения данных за неделю
 * в виде списка, где каждый элемент связывает дату с общей статистикой:
 * шаги, дистанция, калории, белки, жиры, углеводы.
 *
 * @property date Дата, к которой относится статистика (например, 2025-04-05)
 * @property steps Общее количество шагов за день
 * @property distance Пройденное расстояние в метрах
 * @property calories Количество употреблённых калорий
 * @property protein Общее количество белка в граммах
 * @property carbs Общее количество углеводов в граммах
 * @property fats Общее количество жиров (насыщенные + трансжиры) в граммах
 */
data class WeeklyStatItem(
    val date: LocalDate,
    val steps: Int,
    val distance: Double,
    val calories: Double,
    val protein: Double,
    val carbs: Double,
    val fats: Double
)