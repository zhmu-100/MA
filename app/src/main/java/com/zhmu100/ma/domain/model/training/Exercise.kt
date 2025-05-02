package com.zhmu100.ma.domain.model.training

/**
 * Модель упражнения
 *
 * @property name Название упражнения
 * @property duration Продолжительность выполнения в формате ISO-8601
 * @property exerciseType Тип упражнения
 * @property sets Количество подходов (опционально)
 * @property reps Количество повторений (опционально)
 * @property distance Пройденная дистанция в метрах (опционально)
 * @property steps Количество шагов (опционально)
 * @property bmp Частота сердечных сокращений (опционально)
 * @property speed Скорость выполнения в км/ч (опционально)
 * @property weight Используемый вес в кг (опционально)
 * @property calories Потраченные калории (опционально)
 * @property reaction Реакция на выполнение упражнения (опционально)
 * @property note Дополнительные заметки (опционально)
 */
data class Exercise(
    val id: String? = null,
    val name: ExerciseName,
    val duration: String, // ISO-8601 duration
    val exerciseType: ExerciseType,
    val sets: Int? = null,
    val reps: Int? = null,
    val distance: Int? = null,
    val steps: Int? = null,
    val bmp: Int? = null,
    val speed: Int? = null,
    val weight: Int? = null,
    val calories: Double? = null,
    val reaction: ExerciseReaction? = null,
    val note: String? = null
)