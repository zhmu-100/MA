package com.zhmu100.ma.domain.model.diet

import kotlinx.serialization.Serializable

/**
 * Тип приема пищи.
 *
 * Значения:
 * - UNSPECIFIED - Не указан
 * - BREAKFAST - Завтрак
 * - LUNCH - Обед
 * - DINNER - Ужин
 * - SNACK - Перекус
 */
@Serializable
enum class MealType(val typeName: String) {
    MEAL_TYPE_UNSPECIFIED ("Не указано"),
    MEAL_TYPE_BREAKFAST ("Завтрак"),
    MEAL_TYPE_LUNCH ("Обед"),
    MEAL_TYPE_DINNER ("Ужин"),
    MEAL_TYPE_SNACK ("Перекус")
}