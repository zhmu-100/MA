package com.zhmu100.ma.domain.model.training

import kotlinx.serialization.Serializable

/**
 * Перечисление возможных реакций на выполнение упражнения
 */
@Serializable
enum class ExerciseReaction {
    UNSPECIFIED,
    EXCELLENT,
    GOOD,
    OK,
    BAD,
    VERY_BAD
}