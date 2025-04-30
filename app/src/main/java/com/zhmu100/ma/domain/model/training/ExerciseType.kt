package com.zhmu100.ma.domain.model.training

/**
 * Тип упражнения
 * - STATIC: для упражнений без движения (планка, стойка)
 * - DYNAMIC: для упражнений с повторяющимися движениями
 */
enum class ExerciseType {
    UNSPECIFIED,
    STATIC,
    DYNAMIC
}