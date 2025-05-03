package com.zhmu100.ma.ui.data

import com.zhmu100.ma.domain.model.diet.Food
import kotlin.math.roundToInt

/**
 * Представляет продукт питания, добавленный пользователем в один из приемов пищи.
 *
 * @property name Название продукта.
 * @property amount Количество (в граммах или мл) в текстовом формате.
 * @property fat Количество жиров (в граммах).
 * @property carbs Количество углеводов (в граммах).
 * @property protein Количество белков (в граммах).
 * @property calories Количество калорий.
 */

data class FoodItem(
    val name: String,
    val amount: String,
    val fat: Int,
    val carbs: Int,
    val protein: Int,
    val calories: Int
) {
    companion object {
        /**
         * Создаёт [FoodItem] из [Food]
         *
         * @param food Исходный объект [Food], возможно, уже изменённый (например, на 50 г)
         * @param allFood Список всех продуктов (из API), хранящихся на 100 г
         * @return Новый экземпляр [FoodItem]
         */
        fun fromFood(food: Food, allFood: List<Food>): FoodItem {
            var grams = 100.0

            // Пытаемся найти оригинальный продукт по id
            val originalFood = allFood.find { it.id == food.id }
            if (originalFood != null) {
                val caloriesRatio = food.calories / originalFood.calories
                grams = caloriesRatio * 100
            }

            // Округляем до целых граммов
            val amountText = "${grams.roundToInt()} г"

            return FoodItem(
                name = food.name,
                amount = amountText,
                fat = (food.saturatedFats + food.transFats).roundToInt(),
                carbs = food.carbs.roundToInt(),
                protein = food.protein.roundToInt(),
                calories = food.calories.roundToInt()
            )
        }
    }
}