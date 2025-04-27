package com.zhmu100.ma.domain.model.training

/**
 * Модель тренировки
 *
 * @property id Уникальный идентификатор тренировки (опционально при создании)
 * @property name Название тренировки
 * @property date Дата проведения тренировки в формате ISO-8601
 * @property exercises Список упражнений в тренировке (по умолчанию пустой)
 */
data class Workout(
    val id: String? = null,
    val name: String,
    val date: String, // ISO-8601 timestamp
    val exercises: List<Exercise> = emptyList()
)