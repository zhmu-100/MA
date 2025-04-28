package com.zhmu100.ma.domain.model.diet

/**
 * Модель, представляющая прием пищи.
 *
 * @property id Уникальный идентификатор приема пищи (может быть null для новых записей)
 * @property name Название приема пищи
 * @property mealType Тип приема пищи
 * @property foods Список продуктов в приеме пищи
 * @property date Дата приема пищи в формате ISO-8601
 *
 * Пример:
 * ```
 * Meal(
 *     name = "Питательный завтрак",
 *     mealType = MealType.BREAKFAST,
 *     foods = listOf(oatmeal, banana, milk),
 *     date = "2023-05-15T08:30:00Z"
 * )
 * ```
 */
data class Meal(
    val id: String? = null,
    val name: String,
    val mealType: MealType,
    val foods: List<Food> = emptyList(),
    val date: String // ISO-8601 timestamp
)