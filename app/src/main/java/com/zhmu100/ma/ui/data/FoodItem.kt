package com.zhmu100.ma.ui.data

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
)