package com.zhmu100.ma.ui.data

/**
 * Класс данных, представляющий информацию о питательных веществах.
 *
 * Содержит четыре основных показателя:
 * @property proteins Количество белков (в граммах)
 * @property fats Количество жиров (в граммах)
 * @property carbs Количество углеводов (в граммах)
 * @property calories Количество калорий (в ккал)
 */
data class NutrientsData (
    val proteins: Float,
    val fats: Float,
    val carbs: Float,
    val calories: Float
)