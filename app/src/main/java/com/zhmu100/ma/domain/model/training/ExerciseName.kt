package com.zhmu100.ma.domain.model.training

import kotlinx.serialization.Serializable

/**
 * Названия упражнений
 */
@Serializable
enum class ExerciseName(val displayName: String) {
    EXERCISE_NAME_UNSPECIFIED("Не указано"),
    EXERCISE_NAME_PUSHUPS("Отжимания"),
    EXERCISE_NAME_PULLUPS("Подтягивания"),
    EXERCISE_NAME_SQUATS("Приседания"),
    EXERCISE_NAME_PLANK("Планка"),
    EXERCISE_NAME_RUNNING("Бег"),
    EXERCISE_NAME_CYCLING("Велосипед");

    companion object {
        fun fromDisplayName(displayName: String): ExerciseName {
            return entries.firstOrNull { it.displayName == displayName } ?: EXERCISE_NAME_UNSPECIFIED
        }
    }
}