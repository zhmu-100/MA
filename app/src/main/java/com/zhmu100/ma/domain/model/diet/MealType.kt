package com.zhmu100.ma.domain.model.diet

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
enum class MealType(val typeName: String) {
    UNSPECIFIED("Не указано"),
    BREAKFAST("Завтрак"),
    LUNCH("Обед"),
    DINNER("Ужин"),
    SNACK("Перекус")
}