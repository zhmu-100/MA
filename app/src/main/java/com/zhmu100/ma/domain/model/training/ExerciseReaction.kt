package com.zhmu100.ma.domain.model.training

import kotlinx.serialization.Serializable

/**
 * Перечисление возможных реакций на выполнение упражнения
 */
@Serializable
enum class ExerciseReaction {
    EXERCISE_REACTION_UNSPECIFIED,
    EXERCISE_REACTION_EXCELLENT,
    EXERCISE_REACTION_GOOD,
    EXERCISE_REACTION_OK,
    EXERCISE_REACTION_BAD,
    EXERCISE_REACTION_VERY_BAD
}