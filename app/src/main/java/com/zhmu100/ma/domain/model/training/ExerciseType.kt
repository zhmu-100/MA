package com.zhmu100.ma.domain.model.training

import kotlinx.serialization.Serializable

/**
 * Тип упражнения
 * - STATIC: для упражнений без движения (планка, стойка)
 * - DYNAMIC: для упражнений с повторяющимися движениями
 */
@Serializable
enum class ExerciseType {
    EXERCISE_TYPE_UNSPECIFIED,
    EXERCISE_TYPE_STATIC,
    EXERCISE_TYPE_DYNAMIC
}