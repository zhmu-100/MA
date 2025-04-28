package com.zhmu100.ma.domain.model.training

/**
 * Названия упражнений
 */
enum class ExerciseName(val displayName: String) {
    UNSPECIFIED("Не указано"),
    PUSHUPS("Отжимания"),
    PULLUPS("Подтягивания"),
    SQUATS("Приседания"),
    PLANK("Планка"),
    RUNNING("Бег"),
    CYCLING("Велосипед");

    companion object {
        fun fromDisplayName(displayName: String): ExerciseName {
            return entries.firstOrNull { it.displayName == displayName } ?: UNSPECIFIED
        }
    }
}