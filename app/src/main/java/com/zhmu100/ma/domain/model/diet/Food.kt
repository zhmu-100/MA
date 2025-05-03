package com.zhmu100.ma.domain.model.diet

/**
 * Модель, представляющая пищевой продукт.
 *
 * @property id Уникальный идентификатор продукта (может быть null для новых записей)
 * @property name Название продукта
 * @property description Описание продукта
 * @property calories Калорийность (в ккал)
 * @property protein Содержание белка (в граммах)
 * @property carbs Содержание углеводов (в граммах)
 * @property saturatedFats Содержание насыщенных жиров (в граммах)
 * @property transFats Содержание трансжиров (в граммах)
 * @property fiber Содержание клетчатки (в граммах)
 * @property sugar Содержание сахара (в граммах)
 * @property vitamins Список витаминов в составе
 * @property minerals Список минералов в составе
 *
 * Пример:
 * ```
 * Food(
 *     name = "Яблоко",
 *     description = "Свежее зеленое яблоко",
 *     calories = 52.0,
 *     protein = 0.3,
 *     carbs = 14.0,
 *     saturatedFats = 0.0,
 *     transFats = 0.0,
 *     fiber = 2.4,
 *     sugar = 10.0,
 *     vitamins = listOf(vitaminC),
 *     minerals = listOf(potassium)
 * )
 * ```
 */
data class Food(
    val id: String? = null,
    val name: String,
    val description: String,
    val calories: Double,
    val protein: Double,
    val carbs: Double,
    val saturatedFats: Double,
    val transFats: Double,
    val fiber: Double,
    val sugar: Double,
    val vitamins: List<Vitamin> = emptyList(),
    val minerals: List<Mineral> = emptyList()
) {
    /**
     * Создаёт копию текущего продукта с учетом указанной массы.
     *
     * Все значения масштабируются пропорционально переданному весу.
     * Например, если продукт описан на 100 г, а grams = 50 — все значения будут делены на 2.
     */
    fun withPortion(grams: Double): Food {
        val factor = grams / 100.0

        return copy(
            calories = calories * factor,
            protein = protein * factor,
            carbs = carbs * factor,
            saturatedFats = saturatedFats * factor,
            transFats = transFats * factor,
            fiber = fiber * factor,
            sugar = sugar * factor,
            vitamins = vitamins.map { it.withPortion(factor) },
            minerals = minerals.map { it.withPortion(factor) }
        )
    }
}